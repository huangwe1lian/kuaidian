package main

import (
    "archive/zip"
    "bytes"
    "fmt"
    "io"
    "io/ioutil"
    "log"
    "net/http"
    "os"
    "path/filepath"
    "strings"
    "time"
)

var templatePath = "/Users/chenxiaohu/svn_root/pcgeli/"
// var templatePath = "/app/gelitool/pcgeli"


// 给力2.0 应用生成器主函数
// 启动Web服务器提供服务
func main() {
    log.Printf("Geli 2.0 Application Generator ListenAndServe on port: 9090")
    http.HandleFunc("/", handleHttp)
    err := http.ListenAndServe(":9090", nil)
    if err != nil {
        log.Fatal("ListenAndServe: ", err)
    }
}


// 处理应用生成请求
// 未指定app_code, 显示页面提示用户输入
// 指定app_code, 直接声称文件内容给用户下载
func handleHttp(out http.ResponseWriter, r *http.Request) {

    // 处理参数
    r.ParseForm()
    app_code := r.Form["app_code"]
    app := ""
    if app_code != nil && len(app_code) > 0 {
        app = app_code[0]
    }

    // 未指定app_code, 显示页面
    if len(app) < 1 {
        fmt.Fprintf(out,
`<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>给力2.0应用生成器</title>
</head>
<body>
    <h1>给力2.0应用生成器</h1>
    <form method="get">
        <p>
        请输入应用编码，必须符合java包的命名规范：
        </p>
        <input name="app_code" placeholder="应用编码" autofocus required>
        <input type="submit" style="-webkit-appearance:button; font-size:14px;" value="创建并下载">
    </form>
</body>
</html>
`)
        return
    }

    // 根据app_code生成应用
    log.Printf("Start generate for app_code: %v", app)

    // 输出相应头部信息，指定文件名称
    out.Header().Set("Cache-Control", "no-cache")
    out.Header().Set("Cache-Control", "no-store")
    out.Header().Set("Content-Disposition", "attachment;filename=" + app + ".zip")

    // 输出zip文件内容
    generateAndWriteZipContent(out, app)

    log.Printf("Finish generate for app_code: %v", app)
}


// 生成给力应用，并以zip文件形式写出到HTTP响应
// 参数out: HTTP输出的Writer
// 参数app: 用户输入的app_code
func generateAndWriteZipContent(out http.ResponseWriter, app string) {

    // 准备压缩缓冲区
    buf := new(bytes.Buffer)
    zw := zip.NewWriter(buf)

    // 遍历目录, 按需转换并压缩文件到缓冲区
    filepath.Walk(templatePath, func(path string, info os.FileInfo, err error) error {
        rpath := path[len(templatePath):]
        
        if ("" == rpath || strings.HasPrefix(rpath, ".svn") || strings.HasPrefix(rpath, "target")) {
            return nil
        }

        if info.IsDir() {
            // 生成目录，路径必须以 / 结尾
            if strings.HasPrefix(rpath, "src/main/java/cn/pconline/pcgeli") {
                CreateZipItem(zw, strings.Replace(rpath, "pcgeli", app, 1) + "/")
            } else {
                CreateZipItem(zw, rpath + "/")
            }
        } else {
            // 生成文件
            if handleFile(rpath) {
                zipAndTransFile(path, strings.Replace(rpath, "pcgeli", app, 1), app, zw)
            } else {
                zipFile(path, rpath, zw)
            }
        }

        return nil
    })

    // 将缓冲区写入HTTP相应Writer
    zw.Close()
    out.Write(buf.Bytes())
}


// 转换文件并以指定到名称加入zip文件
// 参数from：原始文件名
// 参数file：要加入zip文件的名称
// 参数app： 应用编码，用于转换文件内容
// 参数zw：  zip写入缓冲区
func zipAndTransFile(from string, file string, app string, zw *zip.Writer) {
    // 读取源文件
    data, err := ioutil.ReadFile(from)
    if (err != nil) {
        log.Fatal("Read file err!", err)
    }
    // 替换内容
    str := string(data)
    data2 := []byte(strings.Replace(str, "pcgeli", app, -1))

    // 准备写入zip的条目
    var w io.Writer
    w, err = CreateZipItem(zw, file)
    if (err != nil) {
        log.Fatal("Create zip item err!", err)
    }

    // 内容写入zip文件
    _, err = w.Write(data2)
    if (err != nil) {
        log.Fatal("Write zip err!", err)
    }
}



// 将文件以指定到名称加入zip文件
// 参数from：原始文件名
// 参数file：要加入zip文件的名称
// 参数zw：  zip写入缓冲区
func zipFile(from string, file string, zw *zip.Writer) {
    // 读取源文件
    data, err := ioutil.ReadFile(from)
    if (err != nil) {
        log.Fatal("Read file err!", err)
    }

    // 准备写入zip的条目
    var w io.Writer
    w, err = CreateZipItem(zw, file)
    if (err != nil) {
        log.Fatal("Create zip item err!", err)
    }

    // 内容写入zip文件
    _, err = w.Write(data)
    if (err != nil) {
        log.Fatal("Write zip err!", err)
    }
}


// 创建zip中的文件（或目录）项，并返回写入器
// 参数zw：  zip写入缓冲区
// 参数name：要写入zip的名称，目录必须以 / 结尾
func CreateZipItem(zw *zip.Writer, name string) (io.Writer, error) {
    header := &zip.FileHeader{
        Name:   name,
        Method: zip.Deflate,
    }
    // 设置时间，注意时区计算!
    t := time.Now()
    _, seconds := t.Zone()
    header.SetModTime(t.Add(time.Duration(seconds) * time.Second))
    return zw.CreateHeader(header)
}

// 判断是否需要转换文件路径和内容
// 参数file: 文件名称，符合加入zip文件的相对路径
func handleFile(file string) bool {
    files := []string {
        "pom.xml",
        "nb-configuration.xml",
        "src/main/java/cn/pconline/pcgeli/web/AuthFilter.java",
        "src/main/webapp/admin/index.jsp",
        "src/main/webapp/admin/login.jsp",
        "src/main/webapp/admin/msg.jsp",
        "src/main/webapp/WEB-INF/applicationContext.xml",
        "src/main/webapp/WEB-INF/web.xml",
        "src/main/webapp/WEB-INF/pcgeli-servlet.xml",
    }
    for _, value := range files {
        if file == value {
            return true
        }
    }
    return false
}
