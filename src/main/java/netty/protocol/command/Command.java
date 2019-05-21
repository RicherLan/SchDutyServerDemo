package netty.protocol.command;

public interface Command {

	// 心跳
	int HEARTBEAT_REQUEST = 1;
	int HEARTBEAT_RESPONSE = 2;

	// 登陆
	int LOGIN_REQUEST = 3;
	int LOGIN_RESPONSE = 4;

	// 登出
	int LOGOUT_REQUEST = 5;
	int LOGOUT_RESPONSE = 6;

	// 修改密码
	int UPPASSWORD_REQUEST = 7;
	int UPPASSWORD_RESPONSE = 8;

	// 注册用户 测试阶段
	int REGISTERTEST_REQUEST = 9;
	int REGISTERTEST_RESPONSE = 10;

	// 用户注册账号时 判断提交的手机号是否已经被注册
	int IsPhRegisted_REQUEST = 11;
	int IsPhRegisted_RESPONSE = 12;

	// 群聊发送消息 字符串
	int SendGroupChatText_REQUEST = 13;
	int SendGroupChatText_RESPONSE = 14;
	// 群聊发送消息 字符串 信息包
	int GroupChatText_PACKET = 15;
	int GroupChatText_RESPONSE = 16;

	// 群聊发送消息 语音 照片
	int SendGroupChatFile_REQUEST = 17;
	int SendGroupChatFile_RESPONSE = 18;
	// 群聊发送消息 语音 照片 信息包
	int GroupChatFile_PACKET = 19;
	int GroupChatFile_RESPONSE = 20;

	// 群聊 接收方接收消息后 回执该消息已读
	int ReadGroupChatMsg_REQUEST = 21;
	int ReadGroupChatMsg_RESPONSE = 22;

	// 单人聊天 发送消息 字符串
	int SendSingleChatText_REQUEST = 23;
	int SendSingleChatText_RESPONSE = 24;
	// 单人聊天发送消息 字符串 信息包
	int SingleChatText_PACKET = 25;
	int SingleChatText_RESPONSE = 26;

	// 单人聊天 发送消息 语音 照片
	int SendSingleChatFile_REQUEST = 27;
	int SendSingleChatFile_RESPONSE = 28;
	// 单人聊天发送消息 文件 信息包
	int SingleChatFile_PACKET = 29;
	int SingleChatFile_RESPONSE = 30;
	// 单人聊天 接收方接收消息后 回执该消息已读
	int ReadSingleChatMsg_REQUEST = 31;
	int ReadSingleChatMsg_RESPONSE = 32;

	// 拿到单人聊天 未读信息 一般是刚登陆的时候
	int GetSingleChatMsgNotRead_REQUEST = 33;
	int GetSingleChatMsgNotRead_RESPONSE = 34;

	int GetSingleChatTextNotRead_PACKET = 35; // 发送多条未读的文字消息

	// 拿到群聊天 未读信息 一般是刚登陆的时候
	int GetGroupChatMsgNotRead_REQUEST = 36;
	int GetGroupChatMsgNotRead_RESPONSE = 37;

	int GetGroupChatTextNotRead_PACKET = 38; // 发送多条未读的文字消息

	// 获得某用户的user表中的基本信息
	int GetFriendInfoByID_REQUEST = 39;
	int GetFriendInfoByID_RESPONSE = 40;

	// 获得某用户的群的基本信息
	int GetGroupsInfoOfUser_REQUEST = 41;
	int GetGroupsInfoOfUser_RESPONSE = 42;

	// 获得自己的所有好友信息 一般是刚登陆
	int GetAllFriendInfo_REQUEST = 43;
	int GetAllFriendInfo_RESPONSE = 44;

	// 获得自己的所有好友信息 一般是客户端刷新好友列表时用到
	int FreshAllFriendInfo_REQUEST = 45;
	int FreshAllFriendInfo_RESPONSE = 46;

	// ************************************************************
	// 获得好友头像
	int GetFriendIconOfUID_REQUEST = 47;
	int GetFriendIconOfUID_RESPONSE = 48;

	// *************************************************************
	// 刷新 消息界面
	int FreshNotification_REQUEST = 49;
	int FreshNotification_RESPONSE = 50;

	// 获得在线的好友有哪些 就是返回在线的账号就行
	int GetFriendIDOnline_REQUEST = 51;
	int GetFriendIDOnline_RESPONSE = 52;

	// 删除自己的某一个好友
	int DeleteFriend_REQUEST = 53;
	int DeleteFriend_RESPONSE = 54;
	int FDeleteMe_Packet = 55; // 被删除的一方收到
	// 好友删除自己时 自己收到被删除的消息 要回执
	int ReciveFDeleteMe_REQUEST = 56;
	int ReciveFDeleteMe_RESPONSE = 57;

	// 退群
	int ExitGroup_REQUEST = 58;
	int ExitGroup_RESPONSE = 59;
	int SendExitGroupToAdmi_PACKET = 60; // 发给管理员
	// 群成员退群时 群管理员收到退群消息时 回执
	int GroupAdmiReciveExitGroup_REQUEST = 61;
	int GroupAdmiReciveExitGroup_RESPONSE = 62;

	// 好友或群请求 收到消息后 更改消息的读取状态
	int UpRequestMsgState_REQUEST = 63;
	int UpRequestMsgState_RESPONSE = 64;

	// 添加好友
	int AddFriend_REQUEST = 65;
	int AddFriend_RESPONSE = 66;
	int AddMeAsFriend_PACKET = 67; // 被添加方收到

	// 用户添加自己为好友 自己给回复 同意还是不同意
	int AddMeFriendReturn_REQUEST = 68;
	int AddMeFriendReturn_RESPONSE = 69;

	int AgreeYourFriendRequest_PACKET = 70; // 对方同意你的好友请求
	int DisAgreeYourFriendRequest_PACKET = 71; // 对方不同意你的好友请求

	// 添加好友时 对方同意或拒绝了你的请求 你已经读到了对方同意还是拒绝 给服务器回执
	int AddFriendResult_REQUEST = 72;
	int AddFriendResult_RESPONSE = 73;

	// 申请加群
	int AddGroup_REQUEST = 74;
	int AddGroup_RESPONSE = 75;
	int SendAdmiAddGroup_Packet = 76; // 加群请求发给管理员

	// 用户申请加群时 管理员同意或不同意 回执
	int AddGroupReturn_REQUEST = 77;
	int AddGroupReturn_RESPONSE = 78;
	int SendUserAddGroupResult_PACKET = 79; // 管理员做出同意或不同意 发给用户
	// ************************************************************************************
	int SendAdmiAddGroupHandlered_PACKET = 80; // 通知其他管理员 加群请求已经被其他管理员处理了

	// 申请加群的用户已经收到管理员的同意还是不同意 现在回执
	int ReadAddGroupResult_REQUEST = 81;
	int ReadAddGroupResult_RESPONSE = 82;

	// 拿到所有的好友、群请求信息 一般在刚登陆的时候
	int GetRequestFriendOrGroup_REQUEST = 83;
	int GetRequestFriendOrGroup_RESPONSE = 84;

	// 获得个人信息 一般是刚登陆的时候
	int GetPersonalInfo_REQUEST = 85;
	int GetPersonalInfo_RESPONSE = 86;

	// 客户端添加某人好友时 搜索账号 只需要看到其账号 网名 头像就行
	int SearchAddUser_REQUEST = 87;
	int SearchAddUser_RESPONSE = 88;

	// 客户端添加群时 搜索账号 只需要看到其账号 网名 头像就行
	int SearchAddGroup_REQUEST = 89;
	int SearchAddGroup_RESPONSE = 90;

	// 更改自己的头像
	int UpPersonalIcon_REQUEST = 91;
	int UpPersonalIcon_RESPONSE = 92;

	// 获取自己的的头像
	int GetPerIcon_REQUEST = 93;
	int GetPerIcon_RESPONSE = 94;

	// 修改个人资料
	int UpPeronalInfo_REQUEST = 95;
	int UpPeronalInfo_RESPONSE = 96;

	// 修改好友的备注
	int UpFriendRemark_REQUEST = 97;
	int UpFriendRemark_RESPONSE = 98;

	// 客户端添加好友成功后 将好友加入到自己的好友列表 因此需要好友的资料
	int AddUserToFriendList_REQUEST = 99;
	int AddUserToFriendList_RESPONSE = 100;

	// 修改自己的群名片
	int UpGroupRemark_REQUEST = 101;
	int UpGroupRemarkt_RESPONSE = 102;

	// 修改自己的部室
	int UpGroupPart_REQUEST = 103;
	int UpGroupPart_RESPONSE = 104;

	// 获得某群的所有成员 没有头像
	int GetGroupAllUser_REQUEST = 105;
	int GetGroupAllUser_RESPONSE = 106;
	// 获得某群的所有成员后 向服务器回执 来让服务器发头像
	int GetGroupUserIconByPh_REQUEST = 107;
	int GetGroupUserIconByPh_RESPONSE = 108;

	// 创建社团
	int CreateCorpration_REQUEST = 109;
	int CreateCorpration_RESPONSE = 110;

	// 创建群
	int CreateGroup_REQUEST = 111;
	int CreateGroup_RESPONSE = 112;

	// 加群 或 创建群成功后 把该群的信息加入到自己的群列表中
	int AddGroupToGroupList_REQUEST = 113;
	int AddGroupToGroupList_RESPONSE = 114;

	// 获取课表
	int GetCourseSchOfUID_REQUEST = 115;
	int GetCourseSchOfUID_RESPONSE = 116;

	// 获取成绩
	int GetScoreOfUID_REQUEST = 117;
	int GetScoreOfUID_RESPONSE = 118;

	// 社团组织成员导入自己的课表
	int LoadCourseOfCorp_REQUEST = 119;
	int LoadCourseOfCorp_RESPONSE = 120;

	// 安排值班表
	int SchduleArrangement_REQUEST = 121;
	int SchduleArrangement_RESPONSE = 122;
	int SendAdmiDutySche_PACKET = 123; // 给社团管理(就是安排值班的人)发送排班结果
	int SendAllUsersDutyNotiNO_PACKET = 124; // 把值班结果的编号发送给该社团的所有人

	// 获得自己加入的某个组织的值班表
	int GetSDOfGid_REQUEST = 125;
	int GetSDOfGid_RESPONSE = 126;
	// 获得自己加入的所有组织的值班表
	int GetAllSD_REQUEST = 127;
	int GetAllSD_RESPONSE = 128;

	// 获得自己的未读的值班的通知
	int GetAllDutyNotiNotRead_REQUEST = 129;
	int GetAllDutyNotiNotRead_RESPONSE = 130;

	// 已经读取值班的通知 向服务器反馈
	int ReadDutyNoti_REQUEST = 131;
	int ReadDutyNoti_RESPONSE = 132;

	// 社团组织获得 获得某几节课都有空的人
	int SearchEmptyCourse_REQUEST = 133;
	int SearchEmptyCourse_RESPONSE = 134;

	// 社团组织查看课表导入情况
	int CorpLoadCourseRs_REQUEST = 135;
	int CorpLoadCourseRs_RESPONSE = 136;

	// 社团组织群管理修改学年学期
	int AlterCorpYearTerm_REQUEST = 137;
	int AlterCorpYearTerm_RESPONSE = 138;

	// 添加社团组织的某一个部门
	int AddCorApPart_REQUEST = 139;
	int AddCorApPart_RESPONSE = 140;

	// 删除社团组织的某一个部门
	int DeleteCorpPart_REQUEST = 141;
	int DeleteCorpPart_RESPONSE = 142;
	// 删除部门 还要通知该部室的成员 暂时只把成员的部室设为空 不进行通知
	int SendUserDeleteCorpPart_PACKET = 143;

	// 修改社团组织的某一个部门的名字
	int AlterCorpPart_REQUEST = 144;
	int AlterCorpPart_RESPONSE = 145;
	// 修改部门 还要通知该部室的成员 不进行通知
	int SendUserAlterCorpPart_PACKET = 146;

	// 修改自己在社团的职位
	int AlterCorpPos_REQUEST = 147;
	int AlterCorpPos_RESPONSE = 148;

	/* 动态 */
	// 进入用户资料界面 请求某一条动态 获得基本内容 没有图片
	int GetUDtByDTID_REQUEST = 149;
	int GetUDtByDTID_RESPONSE = 150;
	// 进入用户资料界面 请求某一条动态 获得图片
	int GetUDtICByDTICID_REQUEST = 151;
	int GetUDtICByDTICID_RESPONSE = 152;

	// 进入用户资料界面 用户下拉刷新动态的页面 就是加载新的动态 返回6条动态的id ph2是要查询的人的账号
	int GetUNDtIDs_REQUEST = 153;
	int GetUNDtIDs_RESPONSE = 154;

	// 进入用户资料界面 用户上拉刷新动态的页面 就是加载旧的动态 返回6条动态的id
	// 从当前的dongtaiid开始往前找6条以前的
	int GetUsODtIDs_REQUEST = 155;
	int GetUsODtIDs_RESPONSE = 156;

	// 请求某一条动态 获得基本内容
	int GetDongtaiByDTID_REQUEST = 157;
	int GetDongtaiByDTID_RESPONSE = 158;
	// 请求某一条动态 获得动态主人头像
	int GetDongtaiUICByDTID_REQUEST = 159;
	int GetDongtaiUICByDTID_RESPONSE = 160;
	// 请求某一条动态 获得动态的图片
	int GetDongtaiICByDTID_REQUEST = 161;
	int GetDongtaiICByDTID_RESPONSE = 162;

	// 用户下拉刷新动态的页面 就是加载新的动态 返回6条动态的id
	int GetNewDongtaiIDs_REQUEST = 163;
	int GetNewDongtaiIDs_RESPONSE = 164;

	// 用户上拉刷新动态的页面 就是加载旧的动态 返回6条动态的id
	// 从当前的dongtaiid开始往前找6条以前的
	int GetOldDongtaiIDs_REQUEST = 165;
	int GetOldDongtaiIDs_RESPONSE = 166;

	// 发表动态 普通用户的话 类型用user表示
	int AddDongtai_REQUEST = 167;
	int AddDongtai_RESPONSE = 168;

	// 用户发表动态时 图片分开传送
	int AddDongTaiImage_REQUEST = 169;
	int AddDongTaiImage_RESPONSE = 170;

	// *********************************************
	// 获得动态消息的内容
	int GetDTMsgById_REQUEST = 171;
	int GetDTMsgById_RESPONSE = 172;

	// 获得动态的第一张图片和内容
	int GetDTFirstImAndtextById_REQUEST = 173;
	int GetDTFirstImAndtextById_RESPONSE = 174;

	// *****************************************************
	// 动态消息的先不管

	// 用户下拉刷新动态消息的页面 就是加载新的动态消息 返回6条动态消息的id
	int GetNewDongtaiMsgIDs_REQUEST = 175;
	int GetNewDongtaiMsgIDs_RESPONSE = 176;

	// 用户上拉刷新动态消息的页面 就是加载旧的动态消息 返回6条动态消息的id
	// 从当前的id开始往前找6条以前的
	int GetOldDongtaiMsgIDs_REQUEST = 177;
	int GetOldDongtaiMsgIDs_RESPONSE = 178;

	// 进入某动态的所有评论界面 刚开始要刷新 返回根评论总共10条 大的评论下最多回执3条
	int GetNewComByDongtaiId_REQUEST = 179;
	int GetNewComByDongtaiId_RESPONSE = 180;

	// 进入某动态的所有评论界面 刚开始要刷新 返回根评论总共10条 大的评论下最多回执3条 获得头像
	int GetRtComUIC_REQUEST = 181;
	int GetRtComUIC_RESPONSE = 182;

	// 进入某动态的所有评论界面 上拉刷新 返回根评论总共10条 大的评论下最多回执3条 没有头像
	int GetOldComByDongtaiId_REQUEST = 183;
	int GetOldComByDongtaiId_RESPONSE = 184;

	// 用户给某一条动态点赞
	int DongtaiPraise_REQUEST = 185;
	int DongtaiPraise_RESPONSE = 186;

	// 收到了别人给自己的动态的点赞 回执服务器已读
	int ReciveDongtaiPraise_REQUEST = 187;
	int ReciveDongtaiPraise_RESPONSE = 188;

	// 给动态评论
	int DongtaiComment_REQUEST = 189;
	int DongtaiComment_RESPONSE = 190;

	// 给动态的评论评论
	int DongTaicommentCommnet_REQUEST = 191;
	int DongTaicommentComment_RESPONSE = 192;

	// 收到了别人给自己的动态的评论 回执服务器已读
	int ReciveDongtaiComment_REQUEST = 193;
	int ReciveDongtaiComment_RESPONSE = 194;

	// 拿到自己的动态消息 一般是刚上线的时候
	int GetDongtaiMsg_REQUEST = 195;
	int getDongtaiMsg_RESPONSE = 196;

	// 动态消息的先不管
	// *****************************************************

	// 进入某人的个人页面时 获得其基本信息
	int GetIndexInfoOfPh_REQUEST = 197;
	int GetIndexInfoOfPh_RESPONSE = 198;

	// 进入某人的个人页面时 获得其头像
	int GetIndexICOfPh_REQUEST = 199;
	int GetIndexICOfPh_RESPONSE = 200;

	// 社团组织任命职位
	int AppointCorpPos_REQUEST = 201;
	int AppointCorpPos_RESPONSE = 202;

	// 添加好友 被添加方要获得对方的头像
	int GetUserIcOfAddMeAsFriend_REQUEST = 203;
	int GetUserIcOfAddMeAsFriend_RESPONSE = 204;

	// 对方同意了你的好友请求 获得对方的头像
	int GetUIcOfAgreeYourFriend_Request = 205;
	int GetUIcOfAgreeYourFriend_RESPONSE = 206;

	// 添加好友时 首先查询 获得对方的头像
	int GetUICOfSearchAddUser_Request = 207;
	int GetUICOfSearchAddUser_RESPONSE = 208;

	// 更改群头像
	int UpGroupIcon_Request = 209;
	int UpGroupIcon_RESPONSE = 210;

	// 获得某群头像
	int GetGroupIcByGid_Request = 211;
	int GetGroupIcByGid_RESPONSE = 212;

	// 获得某群头像 客户端加载消息通知时 若本机没有头像 那么向服务器请求
	int GetNotiIconOfGroup_Request = 213;
	int GetNotiIconOfGroup_RESPONSE = 214;

	// 获得某用户头像 客户端加载消息通知时 若本机没有头像 那么向服务器请求
	int GetNotiIconOfUser_Request = 215;
	int GetNotiIconOfUser_RESPONSE = 216;

	// 进入好友列表 如果本地没有好友的头像 获取好友的的头像
	int GetFriendListUIcByPh_Request = 217;
	int GetFriendListUIcByPh_RESPONSE = 218;

	// 添加群时 首先查询 获得群的头像
	int GetGroupICOfSearchAddGroup_Request = 219;
	int GetGroupICOfSearchAddGroup_RESPONSE = 220;

	// 添加群 群管理员要获得对方的头像
	int GetUserIcOfAddGroup_REQUEST = 221;
	int GetUserIcOfAddGroup_RESPONSE = 222;

	// 获得某用户头像
	int GetUserIcOfPh_REQUEST = 223;
	int GetUserIcOfPh_RESPONSE = 224;

	// 获得用户的某群的信息 一般用在更新某群信息时
	int GetGroupInfoByGid_REQUEST = 225;
	int GetGroupInfoByGid_RESPONSE = 226;

	// 自己登陆 有人异地登陆 顶替自己
	int loginByOther_Packet = 227;

	//查詢空教室
	int SearchEmptyClassroom_REQUEST = 228;
	int SearchEmptyClassroom_RESPONSE = 229;
	
	
}
