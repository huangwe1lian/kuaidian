package cn.com.fawtoyota.util;


import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;

public class StringUtils extends org.apache.commons.lang.StringUtils{
	
	private static String XSSCHAR_FILTER = "[<>;'\"%\\(\\)/]";
	
	private static String XSSCHAR_FILTER2 = "[<>;'\"%\\(\\)]";
	

    /**
     * Determine whether a (trimmed) string is empty
     *
     * @param foo The text to check.
     * @return Whether empty.
     */
    public static final boolean isEmpty(String foo) {
        return (foo == null || foo.trim().length() == 0);
    }

    /**
     * Returns the output of printStackTrace as a String.
     *
     * @param e A Throwable.
     * @return A String.
     */
    public static final String stackTrace(Throwable e) {
        String foo = null;
        try {
            // And show the Error Screen.
            ByteArrayOutputStream buf = new ByteArrayOutputStream();
            e.printStackTrace(new PrintWriter(buf, true));
            foo = buf.toString();
        } catch (Exception f) {
            // Do nothing.
        }
        return foo;
    }

    /**
     * Returns the output of printStackTrace as a String.
     *
     * @param e A Throwable.
     * @param addPre a boolean to add HTML <pre> tags around the stacktrace
     * @return A String.
     */
    public static final String stackTrace(Throwable e, boolean addPre) {
        if (addPre) {
            return "<pre>" + stackTrace(e) + "</pre>";
        } else {
            return stackTrace(e);
        }
    }

    /**
     * Compares two Strings, returns true if their values are the
     * same.
     *
     * @param s1 The first string.
     * @param s2 The second string.
     * @return True if the values of both strings are the same.
     */
    public static boolean equals(String s1, String s2) {
        if (s1 == null) {
            return (s2 == null);
        } else if (s2 == null) {
            // s1 is not null
            return false;
        } else {
            return s1.equals(s2);
        }
    }

    public static final int PPKEY_CLASSNAME = 0;
    public static final int PPKEY_ID = 1;
    public static final int PPKEY_PROPERTY = 2;

    /**
     * Takes a String of the form substring[substring]subtring and
     * returns the 3 substrings
     *
     * @return a three element String array
     */
    public static String[] parseObjectKey(String s) {
        String[] p = new String[3];
        StringTokenizer st = new StringTokenizer(s, "[]");
        int count = st.countTokens();
        if (count > 1) {
            p[0] = st.nextToken();
            p[1] = st.nextToken();
            if (count == 3) {
                p[2] = st.nextToken();
            }
        }
        return p;
    }


    /**
     * Remove Underscores from a string and replaces first
     * Letters with Capitals.  foo_bar becomes FooBar
     */
    public static String removeUnderScores(String data) {
        String temp = null;
        StringBuffer out = new StringBuffer();
        temp = data;

        StringTokenizer st = new StringTokenizer(temp, "_");
        while (st.hasMoreTokens()) {
            String element = (String) st.nextElement();
            out.append(firstLetterCaps(element));
        }
        return out.toString();
    }

    /**
     * Makes the first letter caps and leaves the rest as is.
     */
    public static String firstLetterCaps(String data) {
        StringBuffer sbuf = new StringBuffer(data.length());
        sbuf.append(data.substring(0, 1).toUpperCase())
                .append(data.substring(1));
        return sbuf.toString();
    }

    /**
     * Splits the provided CSV text into a list.
     *
     * @param text      The CSV list of values to split apart.
     * @param separator The separator character.
     * @return          The list of values.
     */
    public static String[] split(String text, String separator) {
        StringTokenizer st = new StringTokenizer(text, separator);
        String[] values = new String[st.countTokens()];
        int pos = 0;
        while (st.hasMoreTokens()) {
            values[pos++] = st.nextToken();
        }
        return values;
    }

    /**
     * Joins the elements of the provided array into a single string
     * @param array
     * @param separator
     * @return
     */  
    public static String join(Object array[], String separator) {
        if(array == null) return null;
        if(separator == null) separator = "";
        int arraySize = array.length;
        int bufSize = arraySize != 0 ? arraySize * ((array[0] != null ? array[0].toString().length() : 16) + separator.length()) : 0;
        StringBuffer buf = new StringBuffer(bufSize);
        for(int i = 0; i < arraySize; i++)
        {
            if(i > 0) buf.append(separator);
            if(array[i] != null) buf.append(array[i]);
        }

        return buf.toString();
    }
    
    public static String join(long array[], String separator){
    	if(array == null) return null;
        if(separator == null) separator = "";
        int arraySize = array.length;
        StringBuffer buf = new StringBuffer();
        for(int i = 0; i < arraySize; i++)
        {
            if(i > 0) buf.append(separator);
            buf.append(array[i]);
        }
    	return buf.toString();
    }
    
    public static String join(int array[], String separator){
    	if(array == null) return null;
        if(separator == null) separator = "";
        int arraySize = array.length;
        StringBuffer buf = new StringBuffer();
        for(int i = 0; i < arraySize; i++)
        {
            if(i > 0) buf.append(separator);
            buf.append(array[i]);
        }
    	return buf.toString();
    }
    
    
    public static String concat(Object... argv) {
        StringBuilder buf = new StringBuilder();
        for (Object s : argv) {
        	if (s instanceof List){
        		buf.append(join(((List) s).toArray(), ","));
        	}else{
        		buf.append(s);
        	}
        }
        return buf.toString();
    }

    /**
     * Takes a block of text which might have long lines in it and wraps
     * the long lines based on the supplied wrapColumn parameter. It was
     * initially implemented for use by VelocityEmail. If there are tabs
     * in inString, you are going to get results that are a bit strange,
     * since tabs are a single character but are displayed as 4 or 8
     * spaces. Remove the tabs.
     *
     * @param inString   Text which is in need of word-wrapping.
     * @param newline    The characters that define a newline.
     * @param wrapColumn The column to wrap the words at.
     * @return           The text with all the long lines word-wrapped.
     */

    public static String wrapText(String inString, String newline,
                                  int wrapColumn) {
        StringTokenizer lineTokenizer = new StringTokenizer(
                inString, newline, true);
        StringBuffer stringBuffer = new StringBuffer();

        while (lineTokenizer.hasMoreTokens()) {
            try {
                String nextLine = lineTokenizer.nextToken();

                if (nextLine.length() > wrapColumn) {
                    // This line is long enough to be wrapped.
                    nextLine = wrapLine(nextLine, newline, wrapColumn);
                }

                stringBuffer.append(nextLine);
            } catch (NoSuchElementException nsee) {
                // thrown by nextToken(), but I don't know why it would
                break;
            }
        }

        return (stringBuffer.toString());
    }

    /**
     * Wraps a single line of text. Called by wrapText(). I can't
     * think of any good reason for exposing this to the public,
     * since wrapText should always be used AFAIK.
     *
     * @param line       A line which is in need of word-wrapping.
     * @param newline    The characters that define a newline.
     * @param wrapColumn The column to wrap the words at.
     * @return           A line with newlines inserted.
     */

    protected static String wrapLine(String line, String newline,
                                     int wrapColumn) {
        StringBuffer wrappedLine = new StringBuffer();

        while (line.length() > wrapColumn) {
            int spaceToWrapAt = line.lastIndexOf(' ', wrapColumn);

            if (spaceToWrapAt >= 0) {
                wrappedLine.append(line.substring(0, spaceToWrapAt));
                wrappedLine.append(newline);
                line = line.substring(spaceToWrapAt + 1);
            }

            // This must be a really long word or URL. Pass it
            // through unchanged even though it's longer than the
            // wrapColumn would allow. This behavior could be
            // dependent on a parameter for those situations when
            // someone wants long words broken at line length.
            else {
                spaceToWrapAt = line.indexOf(' ', wrapColumn);

                if (spaceToWrapAt >= 0) {
                    wrappedLine.append(line.substring(0, spaceToWrapAt));
                    wrappedLine.append(newline);
                    line = line.substring(spaceToWrapAt + 1);
                } else {
                    wrappedLine.append(line);
                    line = "";
                }
            }
        }

        // Whatever is left in line is short enough to just pass through,
        // just like a small small kidney stone
        wrappedLine.append(line);

        return wrappedLine.toString();
    }

    public static String replace(String name, char replace, String with) {
        StringBuffer buf = new StringBuffer();
        int begin = 0;
        int end;
        int last = name.length();

        while (true) {
            end = name.indexOf(replace, begin);
            if (end < 0) {
                end = last;
            }
            buf.append(name.substring(begin, end));
            if (end == last) {
                break;
            }
            buf.append(with);
            begin = end + 1;
        }

        return buf.toString();
    }


    public static boolean isLetter(char ch) {
        if ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z'))
            return true;
        else
            return false;
    }
    
    public static String encode(String arg){
		if (isEmpty(arg))
			return "";
		try{
		   return URLEncoder.encode(arg,"GBK");
		}catch (Exception e){
			return "";
		}
	}
    
	public static String encode(String arg,String enc) {
		if (isEmpty(arg))
			return "";
		try {
			return URLEncoder.encode(arg, enc);
		} catch (Exception e) {
			return "";
		}
	}
    
    //去空格
    public static String trim(String arg){
		if (isEmpty(arg))
			return arg;
		return arg.trim();
	}
    
    public static char charValue(String v,char def) {
            if(v==null || v.length()==0)return def;
            try {
                    return (char) Integer.parseInt(v);
            } catch(Exception e) {
                    return def;
            }
    }

    public static int intValue(String v,int def) {
            if(v==null || v.length()==0)return def;
            try {
                    return Integer.parseInt(v.trim());
            } catch(Exception e) {
                    return def;
            }
    }
    
    public static Integer integerValue(String v) {
		return integerValue(v, null);
	}
	
	public static Integer integerValue(String v, int def) {
		if (isBlank(v))
			return new Integer(def);
		try {
			return Integer.valueOf(v);
		} catch (Exception e) {
			return new Integer(def);
		}
	}
	
	public static Integer integerValue(String v, Integer def) {
		if (isBlank(v))
			return def;
		try {
			return Integer.valueOf(v);
		} catch (Exception e) {
			return def;
		}
	}

	public static boolean isBlank(String str) {
		return (str == null || str.trim().length() == 0);
	}
	
	public static  long longValue(String v,long def) {
            if(v==null || v.length()==0)return def;
            try {
                    return Long.parseLong(v.trim());
            } catch(Exception e) {
                    return def;
            }
    }

	public static  boolean booleanValue(String v,boolean def) {
            if(v==null || v.length()==0)return def;

            if (v.equalsIgnoreCase("true") || v.equalsIgnoreCase("yes")) {
                    return true;
            } else if (v.equalsIgnoreCase("false") || v.equalsIgnoreCase("no")) {
                    return false;
            } else {
                    return def;
            }
    }

	public static  float floatValue(String v,float def) {
            if(v==null || v.length()==0)return def;
            try {
                    return Float.parseFloat(v.trim());
            } catch(Exception e) {
                    return def;
            }
    }

	public static  double doubleValue(String v,double def) {
            if(v==null || v.length()==0)return def;
            try {
                    return Double.parseDouble(v.trim());
            } catch(Exception e) {
                    return def;
            }
    }

	public static  Date dateValue(String v,String fm,Date def) {
            if(v==null || v.length()==0)return def;
            try {
                    return new SimpleDateFormat(fm).parse(v.trim());
            } catch(Exception e) {
                    return def;
            }
    }
    

	public static  String stringValue(String v,String def) {
            if(v==null || v.trim().length() == 0 || "null".equals(v)) return def;
            return v.trim();
    }

	public static  String[] stringArrayValue(String[] v,String[] def){
            if(v==null || v.length == 0)return def;
            return v;
    }

	public static  byte byteValue(String v,byte def) {
            if(v==null || v.trim().length()==0)return def;
            try {
                    return Byte.parseByte(v);
            } catch(Exception e) {
                    return def;
            }
    }
    
    //判断是否纯数字
    public static boolean isNumeric(String str){ 
        Pattern pattern = Pattern.compile("\\d*"); 
        return pattern.matcher(str).matches();   
    } 
    
    //判断是否纯中文
    public static boolean isChineseStr(String str){
    	Pattern pattern = Pattern.compile("^[\u4e00-\u9fa5]+$"); 
        return pattern.matcher(str).matches();   
    }
    
    //判断是否空格
    public static boolean isSpace(String str){
    	Pattern pattern = Pattern.compile("^[\\s|\u3000]"); 
        return pattern.matcher(str).matches();   
    }
    
    //判断是否 合法电话(固话、手机)
    public static boolean isPhone(String str){
    	Pattern pattern = Pattern.compile("^\\d{3}(-)?\\d{8}|\\d{4}(-)?\\d{7}|1\\d{10}$");  
    	return pattern.matcher(str).matches();   
    }
    
    //判断是否 合法手机
    public static boolean isMobilePhone(String str){
    	Pattern pattern = Pattern.compile("^1\\d{10}$");  
    	return pattern.matcher(str).matches();
    }
    
    public static boolean is400Phone(String str){
    	Pattern pattern = Pattern.compile("^400[0-9]{7}$");  
    	return pattern.matcher(str).matches();   
    }
    
    public static float countTxt(String str){
    	float count = 0;
    	if( null == str || str.equals("") ) return count;
    	String[] s = str.split("");
    	for(int i=0; i<s.length; i++){
			if (isChineseStr(s[i])){
				count++;
			}else if(isSpace(s[i])){
				if( i!=0 && !isSpace(s[i-1])){
					count += 0.5;
				}
			}else if(s[i].equals("")){
				
			}else{
				count += 0.5;
			}
    	}
    	return count;
    }
	
	@SuppressWarnings("unchecked")
	public static Map convertJSONObject2Map(JSONObject json,Map result,Map target,List targetList){
		if (json==null) return result;
		try{
			for (Iterator its=json.keys();its.hasNext();){
				String key=(String)its.next();
				Object value=json.get(key);
				if (value instanceof JSONObject){
					JSONObject jo=(JSONObject)value;
					//System.out.println(key+"-JSONObject:"+value);
					Map tmp=new HashMap();					
					if (target!=null){
						target.put(key,tmp);
					}else{
						result.put(key,tmp);
					}
					convertJSONObject2Map(jo,result,tmp,null);
				}else if (value instanceof JSONArray){	
					//System.out.println(key+"-JSONArray:"+value);
					List list=new ArrayList();
					if (target!=null){
						target.put(key,list);
					}else{
						result.put(key,list);
					}
					JSONArray ja=(JSONArray)value;
					for(int i=0 ; ja!=null && i<ja.length(); i++){
						Object o=ja.get(i);
						if (o instanceof JSONObject) {
							Map tmp=new HashMap();	
							list.add(tmp);
							convertJSONObject2Map((JSONObject)o,result,tmp,list);
						}else{
							list.add(o);
						}
					}
				}else{
					//System.out.println(key+"-other:"+value);
					if (target!=null){
						target.put(key,value);
					}else{
						result.put(key, value);
					}
				}
			}	 	
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
		return result;	 		 
	}
	
	@SuppressWarnings("unchecked")
	public static void debugPrintln(Map map){
		try{
			for(Iterator it=map.keySet().iterator();it.hasNext();){
				String key=(String)it.next();
				Object value=map.get(key);
				if (value instanceof List) {
					List vl=(List)value;
					for(int i=0;i<vl.size();i++){
						System.out.println("List="+vl.get(i));
						debugPrintln((Map)vl.get(i));
					}		
				}else if (value instanceof Map){ 	
					debugPrintln((Map)value);
				}else{
					System.out.println(key+"="+value);
				}
			}
		}catch(Exception e){}
	}
	
	//----------------------------------------------------------	
	/*
	 * 去丢空格和重复的，重新结合。如：嘿嘿的 ;看看 ;黑河 ; -> 嘿嘿的;看看;黑河 
	 */
	public static String trimRejoin(String s,String s1){
		String rs="";
		if (s!=null && s.trim().length()>0 && s1!=null){
			String[] ss=s.split(s1);
			for(String o:ss){
				if (o!=null && o.trim().length()>0 && rs.indexOf(o+s1)==-1) rs+=o+s1;
			}
			rs=rs.replaceAll(s1+"$", "");
		}else{
			rs=s;
		}
		return rs;
	}
    
	public static int getByteLength(String s) {
		if (s == null) return 0;
		return s.getBytes().length;
	}


	public static String filterScript(String txt){
		if (isBlank(txt)) return txt;
		return txt.replaceAll("(?si)<\\s*\\bscript\\b(.*?)>(.*?)</script>","")
		.replaceAll("(?si)<\\s*\\bstyle\\b(.*?)>(.*?)</style>","");
	}
	
	/** 防止xss攻击，过滤特殊字符,"<>;'"%()/"
	 * @param funcName
	 * @return
	 */
	public static String xssCharFilter(String str){
		return null != str ? str.replaceAll(XSSCHAR_FILTER, "") : null;
	}
	
	/** 防止xss攻击，过滤特殊字符,"<>;'"%()"
	 * @param funcName
	 * @return
	 */
	public static String xssCharFilter2(String str){
		return null != str ? str.replaceAll(XSSCHAR_FILTER2, "") : null;
	}
	
	/**
     * 检测是否有emoji字符
     * @param source
     * @return 一旦含有就抛出
     */
    public static boolean containsEmoji(String source) {
        if (StringUtils.isBlank(source)) {
            return false;
        }

        int len = source.length();

        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);

            if (isEmojiCharacter(codePoint)) {
                //do nothing，判断到了这里表明，确认有表情字符
                return true;
            }
        }

        return false;
    }

    private static boolean isEmojiCharacter(char codePoint) {
        return (codePoint == 0x0) ||
                (codePoint == 0x9) ||
                (codePoint == 0xA) ||
                (codePoint == 0xD) ||
                ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||
                ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) ||
                ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
    }

    /**
     * 过滤emoji 或者 其他非文字类型的字符
     * @param source
     * @return
     */
    public static String filterEmoji(String source) {
    	source += " "; 
        if (!containsEmoji(source)) {
            return source;//如果不包含，直接返回
        }
        //到这里铁定包含
        StringBuilder buf = null;

        int len = source.length();

        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);

            if (isEmojiCharacter(codePoint)) {
                if (buf == null) {
                    buf = new StringBuilder(source.length());
                }

                buf.append(codePoint);
            } else {
            }
        }

        if (buf == null) {
            return source;//如果没有找到 emoji表情，则返回源字符串
        } else {
            if (buf.length() == len) {//这里的意义在于尽可能少的toString，因为会重新生成字符串
                buf = null;
                return source.trim();
            } else {
                return buf.toString();
            }
        }

    }

    public static String string2Unicode(String string) {
	    StringBuffer unicode = new StringBuffer();
	    for (int i = 0; i < string.length(); i++) {
	        // 取出每一个字符
	        char c = string.charAt(i);
	        // 转换为unicode
	        unicode.append("\\u" + Integer.toHexString(c));
	    }
	    return unicode.toString();
	}
	
	public static String unicode2String(String unicode) {
	    StringBuffer string = new StringBuffer();
	    String[] hex = unicode.split("\\\\u");
	    for (int i = 1; i < hex.length; i++) {
	        // 转换出每一个代码点
	        int data = Integer.parseInt(hex[i], 16);
	        // 追加成string
	        string.append((char) data);
	    }
	    return string.toString();
	}
	
	 /**
     * 用逗号串连起来
     *
     * @param arr
     * @return
     */
    public static String toString(Object[] arr) {
        StringBuilder sb = new StringBuilder();
        if (arr != null) {
            for (Object obj : arr) {
                sb.append(obj.toString()).append(",");
            }
        }
        if (sb.length() > 0) {
            return sb.toString().substring(0, sb.length() - 1);
        }
        return "";
    }
    
    
    /**
     * 生成指定位数的随机编码 编码从64钟字符内选取
     * @param size
     * @return
     */
    public static String getRandomCode(int size){
    	
    	String[] codes = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z","a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z","0","1","2","3","4","5","6","7","8","9",":","-"};
    	String result = "";
    	try{
    		Random rand=new Random();
        	int count = 0;
        	while(count < size){
        		String code = codes[rand.nextInt(64)];
        		result = result + code;
        		count++;
        	}
        }catch(Exception e){}
    	return result;
    }
    
    
    public enum JSONType{ 
        /**JSONObject*/     
        OBJECT, 
        /**JSONArray*/ 
        ARRAY, 
        /**不是JSON格式的字符串*/ 
        ERROR 
    } 
    
    /**
     * 获取string的JSON类型
     * @param str
     * @return
     */
    public static JSONType getJSONType(String str){ 
    	if(isEmpty(str)){ 
    		return JSONType.ERROR; 
    	} 
    	final char[] strChar = str.substring(0, 1).toCharArray(); 
    	final char firstChar = strChar[0]; 
    	if(firstChar == '{'){ 
    		return JSONType.OBJECT; 
    	}else if(firstChar == '['){ 
    		return JSONType.ARRAY; 
    	}else{ 
    		return JSONType.ERROR; 
    	} 
    }
    
    /**
     * 获取域名后缀
     * @param serverName
     * @return
     */
    public static String getDomainSuffix(String serverName) {
        if (serverName == null || "".equals(serverName)) {
            return null;
        }
        serverName = serverName.trim();
        if (serverName.matches("^\\d{1,3}.\\d{1,3}.\\d{1,3}.\\d{1,3}$")) {
            return serverName; //IP地址
        }
        int i = serverName.indexOf('.');
        return i < 0 ? serverName : serverName.substring(i);
    }
}