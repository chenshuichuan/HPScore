package hpscore.tools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串处理工具类
 * @author ouzhb
 */
public class StringUtil {
	public static void main(String[] args) {

		System.out.println("getNextCell:"+StringUtil.getNextCell('A',20));
	}
	/**
	 * 判断字符串是否为null、“ ”、“null”
	 * @param obj
	 * @return
	 */
	public static boolean isNull(String obj) {
		if (obj == null){
			return true;
		}else if (obj.toString().trim().equals("")){
			return true;
		}else if(obj.toString().trim().toLowerCase().equals("null")){
			return true;
		}
		
		return false;
	}

	/**
	 * 正则验证是否是数字
	 * @param str
	 * @return
	 */
	public static boolean isNumber(String str) {
		Pattern pattern = Pattern.compile("[+-]?[0-9]+[0-9]*(\\.[0-9]+)?");
		Matcher match = pattern.matcher(str);
		
		return match.matches();
	}

	/**
	 * 正则验证用户名是否合格
	 * @param userName
	 * @return
	 */
	public static boolean isUserName(String userName) {
		Pattern pattern = Pattern.compile("^[\\u4E00-\\u9FA5\\w]{1,}$");
		Matcher match = pattern.matcher(userName);

		return match.matches();
	}

	/**
	 * 正则验证密码是否合格
	 * @param password
	 * @return
	 */
	public static boolean isPassword(String password) {
		Pattern pattern = Pattern.compile("^\\w+$");
		Matcher match = pattern.matcher(password);

		return match.matches();
	}

    /** 
     * 将一个长整数转换位字节数组(8个字节)，b[0]存储高位字符，大端 
     *  
     * @param l 
     *            长整数 
     * @return 代表长整数的字节数组 
     */  
    public static byte[] longToBytes(long l) {  
        byte[] b = new byte[8];  
        b[0] = (byte) (l >>> 56);  
        b[1] = (byte) (l >>> 48);  
        b[2] = (byte) (l >>> 40);  
        b[3] = (byte) (l >>> 32);  
        b[4] = (byte) (l >>> 24);  
        b[5] = (byte) (l >>> 16);  
        b[6] = (byte) (l >>> 8);  
        b[7] = (byte) (l);  
        return b;  
    }

    //比较评委id或作品id，用于按序号由小到大排序
	public static int comparePidOrProId(String pid1,String pid2){
    	int i=0;
    	int id1 = Integer.parseInt(pid1);
		int id2 = Integer.parseInt(pid2);
		if(id1>id2)i=1;
		else if(id1<id2)i=-1;
		return i;
	}
	//由大到小排序
	public static int compareTwoDouble(double score1,double score2){
		int i=0;
		if(score1<score2)i=1;
		else if(score1>score2)i=-1;
		return i;
	}

	//由大到小排序
	public static boolean isEquals(double score1,double score2){
		boolean equals = false;
		if(Double.doubleToLongBits(score1)==Double.doubleToLongBits(score2))equals=true;
		return equals;
	}

	//根据起始单元格，计算结束单元格的大写字母，并返回
	public static String getNextCell(char start,int next){
		int nextCharNumber = ((int)start)+next;
		return String.valueOf((char)(nextCharNumber));
	}
	//获取下一个字母
	public String getNextUpEn(String en){
		if(en==null || en.equals(""))
			return "A";
		char lastE = 'Z';
		int lastEnglish = (int)lastE;
		char[] c = en.toCharArray();
		if(c.length>1){
			return null;
		}else{
			int now = (int)c[0];
			if(now >= lastEnglish)
				return null;
			char uppercase = (char)(now+1);
			return String.valueOf(uppercase);
		}
	}
}
