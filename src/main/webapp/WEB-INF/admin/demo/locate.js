;(function(){
    var Locate = {
        running:false,// false || true
        cookieName:'pcLocate',
        callbackQueue:[],// 定位回调函数队列
        defaultData:{proCode:'440000', pro:'广东省', cityCode:'440100', city:'广州市'},
        dataType:'', //数据来源： 'ipAreaCoordJson' || 'ipJson' || 'cookie' || 'default' || '' || 'user'
        err:[0], //记录错误数量
        /*
            [0]:'', //记录是否有错误： 0 || 1 || 2 || 3 ...
            ['h5']:'', //记录h5定位错误： 'NotHtml5' || 'RejectShare' || 'GetError' || 'Timeout'
            ['ipAreaCoordJson']:'',//记录 ipAreaCoordJson 定位接口返回错误信息
            ['ipJson']:'', //记录 ipJson 定位接口返回错误信息
            ['region_ipArea']:'', //记录 region_ipArea 接口错误信息
            ['interface']:'' //记录接口请求异常，网络或者接口异常： 'ipJson' || 'ipAreaCoordJson'
            ['other']:'' //记录其它异常，如用户设置地区时异常
        */
        extend:function(o,n,override){
            for(var p in n) {
                if(n.hasOwnProperty(p) && (!o.hasOwnProperty(p) || override))o[p]=n[p];
            }
            return o;
        },
        parseQuery: function(obj) {
            var ret = '';
            for (var i in obj) {
                if (obj.hasOwnProperty(i)) {
                    ret += '&' + i + '=' + obj[i];
                }
            }
            return ret;
        },
        parseResult: function(ret) {
            if (!ret) return;
            if (window.JSON && typeof window.JSON.parse === 'function') {
                return JSON.parse(ret)
            } else {
                return eval('(' + ret + ')');
            }
        },
        setCookie: function(name, value, time) {//time=1024 * 24 * 3600 * 1000 //天、小时、秒、毫秒
            document.cookie = name + "=" + encodeURIComponent(value) + "; path=/; domain=" + window.location.hostname.replace(/.+((\.\w+)\.com(\.cn)?)/, "$1") + "; expires=" + new Date(+new Date() + time).toGMTString();
        },
        getCookie: function(name) {
            return decodeURIComponent(document.cookie.replace(new RegExp(".*(?:^|; )" + name + "=([^;]*).*|.*"), "$1"));
        },
        getLocateCookie: function(){
            return Locate.parseResult(Locate.getCookie(Locate.cookieName));
        },
        stringifyResult: (function() {
            var stringfiy = function(input) {
                var isArray = function(arr) {
                    if (typeof Array.isArray === 'function') {
                        return Array.isArray(arr);
                    } else {
                        return Object.prototype.toString.call(arr).slice(8, -1) === 'Array';
                    }
                }
                var isObject = function(obj) {
                    return Object.prototype.toString.call(obj).slice(8, -1) === 'Object';
                }
                var parse = function(input) {
                    if (isArray(input)) {
                        for (var i = 0; i < input.length; i++) {
                            input[i] = parse(input[i]);
                        }
                        return '[' + input.join(',') + ']';
                    } else if (isObject(input)) {
                        var ret = [];
                        for (var i in input) {
                            if (input.hasOwnProperty(i)) {
                                ret.push('"' + i + '":' + parse(input[i]));
                            }
                        }
                        return '{' + ret.join(',') + '}';
                    } else if (typeof input === "string") {
                        return '"' + input.toString() + '"';
                    } else {
                        return input.toString();
                    }
                }
                return parse(input);
            }
            return function(ret) {
                if (window.JSON && typeof window.JSON.stringify === 'function') {
                    return JSON.stringify(ret);
                } else {
                    return stringfiy(ret);
                }
            }
        })(),
        getScriptFuc:{},//用于临时存放请求接口的回调函数

        /**
         * @param {Object} option
         *   {String} option.url 请求接口地址
         *   {String} option.charset 编码
         *   {String} option.interface 请求接口的名称 or 代号，用于记录错误信息中
         *   {Function} option.callbackSucc 请求成功后的回调方法
         *   {String} option.callbackSuccName 指定回调方法名，用于请求时不穿透接口缓存
         *   {Function} option.callbackErr 请求失败后的回调方法
        */
        getScript: function(option) {//url,charset,interface,callbackSucc,callbackSuccName,callbackSuccName,callbackErr
            var script = document.createElement("script");
            var callbackVal = option.callbackSuccName ? option.callbackSuccName : 'callback' + 1*new Date;
            var callbackName = "Locate.getScriptFuc." + callbackVal,
                add  = /\?/.test(option.url) ? "&" : "?";

            Locate.getScriptFuc[callbackVal] = function(json){
                script.jsonp = 1;
                option.callbackSucc(json);
            }
            
            script.type = "text/javascript";
            script.src = option.url + add + 'callback=' + callbackName;
            script.charset = option.charset || "UTF-8";
            script.onload = script.onreadystatechange = function() {
                if (!this.readyState || this.readyState == "loaded" || this.readyState == "complete") {
                    Locate.removeScript(this, option.callbackErr, option.interface, callbackVal);
                }
            }
            script.onerror = function() {
                Locate.removeScript(this, option.callbackErr, option.interface, callbackVal);
            }
            document.getElementsByTagName("head")[0].appendChild(script);
        },
        removeScript: function(script, callbackErr, interface, callbackVal) {
            if (typeof script.jsonp === "undefined") {
                callbackErr(interface);
            }
            if (script.clearAttributes) {
                script.clearAttributes();
            } else {
                script.onload = script.onreadystatechange = script.onerror = null;
            }
            script.parentNode.removeChild(script);
            delete Locate.getScriptFuc[callbackVal];
        },
        getByCoordSucc: function(data) {
            if(data.error) {
                Locate.err[0] += 1;
                Locate.err['ipAreaCoordJson'] = data.error;
                Locate.dataType = 'default';
                Locate.doCallbackErr();
            }else{
                Locate.dataType = 'ipAreaCoordJson';
                Locate.doCallbackSucc(data);
            }
        },
        getByIpSucc: function(data) {
            if(data.err) {
                Locate.err[0] += 1;
                Locate.err['ipJson'] = data.err;
            }
            if(data.cityCode) {
                Locate.dataType = 'ipJson';
                Locate.doCallbackSucc(data);
            }else{
                Locate.dataType = 'default';
                Locate.doCallbackErr();
            }
        },
        getCityErr: function(interface) {
            Locate.err[0] += 1;
            Locate.err['interface'] = interface;
            Locate.dataType = 'default';
            Locate.doCallbackErr();
        },
        getCityByIp: function(args) {
            var url = 'http://whois.pconline.com.cn/ipJson.jsp?rep=pcauto';
            if (args) {
                url += Locate.parseQuery(args);
            }
            Locate.getScript({
                url: url,
                interface: 'ipJson',
                callbackSucc: Locate.getByIpSucc,
                callbackSuccName: 'getByIpSucc',
                callbackErr: Locate.getCityErr,
                charset: 'GBK'
            });
        },
        getCoordsByH5: function() {
            navigator.geolocation.getCurrentPosition(getCityByCoords, getCoordsByH5Error, {timeout: 1e4});
            function getCoordsByH5Error(error) {
                Locate.err[0] += 1;
                switch (error.code) {
                    case 1:
                        Locate.err['h5'] = 'RejectShare';
                        break;
                    case 2:
                        Locate.err['h5'] = 'GetError';
                        break;
                    case 3:
                        Locate.err['h5'] = 'Timeout';
                        break;
                    default:
                        Locate.err['h5'] = 'unknownErr'
                }
                Locate.getCityByIp({
                    'sts': Locate.err['h5']
                });
            }
            function getCityByCoords(position) {
                var lat = position.coords.latitude,
                    lng = position.coords.longitude,
                    url = "http://whois.pconline.com.cn/ipAreaCoordJson.jsp?coords=" + lng + "," + lat + "&level=2";
                Locate.getScript({
                    url: url,
                    interface: 'ipAreaCoordJson',
                    callbackSucc: Locate.getByCoordSucc,
                    callbackSuccName: 'getByCoordSucc',
                    callbackErr: Locate.getCityErr
                });
            }
        },
        doCallbackSucc: function(data) {
            var data = {
                proCode: data.proCode,
                pro: data.pro,
                cityCode: data.cityCode,
                city: data.city,
                dataType: Locate.dataType,
                expires: +new Date + 1296 * 1e6 //过期时间 15天
            }
            Locate.setCookie(Locate.cookieName, Locate.stringifyResult(data), 1024*24*3600*1000);
            data.err = Locate.err;
            Locate.doCallback(data);
        },
        doCallbackErr: function() {
            var data = {};
            Locate.extend(data,Locate.defaultData);
            data.dataType = Locate.dataType;
            data.err = Locate.err;
            Locate.doCallback(data);
        },
        doCallback: function(data) {
            var queue = Locate.callbackQueue, i = 0, length = queue.length;
            for(; i<length; i++) {queue[i].call(Locate, data);}
            Locate.callbackQueue = [];
            Locate.err = [0];
            Locate.dataType = '';
            Locate.running = false;
        },
        // 通过定位城市code:cityCode
        setByCityCode: function(cityCode, callbackSetSucc, callbackSetErr) {
            if(Locate.running) {
                var err = [1];
                err['other'] = 'Lacate is running!';
                callbackSetErr({err:err});
            }else{
                Locate.running = true;
                callbackSetSucc && (Locate.callbackQueue.push(callbackSetSucc));
                callbackSetErr && (Locate.callbackSetErr = callbackSetErr);
                var url = "http://lrc.pcauto.com.cn:8080/api/iparea?cityCode=" + cityCode;
                Locate.getScript({
                    url: url,
                    interface: 'region_ipArea',
                    callbackSucc: Locate.setByCityCodeSucc,
                    callbackSuccName: 'setByCityCodeSucc',
                    callbackErr: Locate.setByCityCodeErr
                });
            }
        },
        setByCityCodeSucc: function(data) {
            if(!data.result) {
                if(Locate.callbackSetErr) {
                    var dataReturn = {err: data.msg};
                    Locate.callbackSetErr(dataReturn);
                    delete Locate.callbackSetErr;
                }
                Locate.running = false;
            }else{
                var dataReturn = {
                    proCode: data.cityData.proCode,
                    pro: data.cityData.pro + '省',
                    cityCode: data.cityData.cityCode,
                    city: data.cityData.name + '市',
                    dataType: 'user',
                    expires: +new Date + 1296 * 1e6 //过期时间 15天
                }
                Locate.setCookie(Locate.cookieName, Locate.stringifyResult(dataReturn), 1024*24*3600*1000);
                dataReturn.err = Locate.err;
                Locate.doCallback(dataReturn);
            }
        },
        setByCityCodeErr: function(interface) {
            if(Locate.callbackSetErr) {
                var err = [1];
                err['other'] = interface + '接口请求失败';
                Locate.callbackSetErr({err:err});
                delete Locate.callbackSetErr;
                Locate.running = false;
            }
        },
        init: function(obj) { //callback, device
            this.callbackQueue.push(obj.callback);
            this.device = obj.device || 'wap';
            if(!this.running) {
                this.running = true;
                var locationCookie = this.getLocateCookie(),
                    now = +new Date;
                if (locationCookie && locationCookie.city && locationCookie.expires > now) {
                    locationCookie.dataType = 'cookie';
                    this.doCallback(locationCookie);
                } else {
                    if(this.device == 'wap') {
                        if (navigator.geolocation) {
                            this.getCoordsByH5();
                        } else {
                            this.err[0] += 1;
                            this.err['h5'] = 'NotHtml5';
                            this.getCityByIp({
                                'sts': this.err['h5']
                            });
                        }
                    }else if(this.device == 'pc') {
                        this.getCityByIp({
                            'sts': 'devicePC'
                        });
                    }
                }
            }
        }
    };
    window.Locate = Locate;
})();