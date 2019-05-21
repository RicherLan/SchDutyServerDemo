package educationSystem;

import java.awt.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class UJN {

	public static String zhuxiaoquName = "主校区";
	public static Map<String,String> zhuxiaoquBuildingNum = new HashMap<String,String>();
	
	public static String shungengName = "舜耕校区";
	public static Map<String,String> shungengBuildingNum = new HashMap<String,String>();
	
	public static String mingshuiName = "明水校区";
	public static Map<String,String> mingshuiBuildingNum = new HashMap<String,String>();
	
	
	public static void init() {
		
		//初始化  主校区的  楼号
		zhuxiaoquBuildingNum.put("0020","0020");
		zhuxiaoquBuildingNum.put("辰星园","0009");
		zhuxiaoquBuildingNum.put("第八教学楼","0011");
		zhuxiaoquBuildingNum.put("第二教学楼","0007");
		zhuxiaoquBuildingNum.put("第七教学楼","0010");
		zhuxiaoquBuildingNum.put("第三教学楼","0003");
		zhuxiaoquBuildingNum.put("第十教学楼","0006");
		zhuxiaoquBuildingNum.put("第十一教学楼","0008");
		zhuxiaoquBuildingNum.put("第四教学楼","0002");
		zhuxiaoquBuildingNum.put("第五教学楼","0016");
		zhuxiaoquBuildingNum.put("第一教学楼","0004");
		zhuxiaoquBuildingNum.put("机械楼 ","0015");
		zhuxiaoquBuildingNum.put("梅花馆","0005");
		zhuxiaoquBuildingNum.put("特教楼","0013");
		zhuxiaoquBuildingNum.put("体育场","0021");
		zhuxiaoquBuildingNum.put("信息楼","0012");
		zhuxiaoquBuildingNum.put("逸夫楼","0014");
		zhuxiaoquBuildingNum.put("音乐楼","0022");
		
		//初始化舜耕校区  
		shungengBuildingNum.put("1001", "1001");
		shungengBuildingNum.put("1002", "1002");
		shungengBuildingNum.put("1003", "1003");
		shungengBuildingNum.put("1004", "1004");
		shungengBuildingNum.put("1005", "1005");
		shungengBuildingNum.put("1100", "1100");
		
		//初始化明水校区
		mingshuiBuildingNum.put("3001","3001");
		mingshuiBuildingNum.put("3002","3002");
		mingshuiBuildingNum.put("3003","3003");
		
	}
}
