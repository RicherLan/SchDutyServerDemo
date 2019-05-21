package netty.server.handler;


import static netty.protocol.command.Command.SearchEmptyClassroom_REQUEST;
import static netty.protocol.command.Command.AddCorApPart_REQUEST;
import static netty.protocol.command.Command.AddDongTaiImage_REQUEST;
import static netty.protocol.command.Command.AddDongtai_REQUEST;
import static netty.protocol.command.Command.AddFriendResult_REQUEST;
import static netty.protocol.command.Command.AddFriend_REQUEST;
import static netty.protocol.command.Command.AddGroupReturn_REQUEST;
import static netty.protocol.command.Command.AddGroupToGroupList_REQUEST;
import static netty.protocol.command.Command.AddGroup_REQUEST;
import static netty.protocol.command.Command.AddMeFriendReturn_REQUEST;
import static netty.protocol.command.Command.AddUserToFriendList_REQUEST;
import static netty.protocol.command.Command.AlterCorpPart_REQUEST;
import static netty.protocol.command.Command.AlterCorpPos_REQUEST;
import static netty.protocol.command.Command.AlterCorpYearTerm_REQUEST;
import static netty.protocol.command.Command.AppointCorpPos_REQUEST;
import static netty.protocol.command.Command.CorpLoadCourseRs_REQUEST;
import static netty.protocol.command.Command.CreateCorpration_REQUEST;
import static netty.protocol.command.Command.CreateGroup_REQUEST;
import static netty.protocol.command.Command.DeleteCorpPart_REQUEST;
import static netty.protocol.command.Command.DeleteFriend_REQUEST;
import static netty.protocol.command.Command.ExitGroup_REQUEST;
import static netty.protocol.command.Command.FreshAllFriendInfo_REQUEST;
import static netty.protocol.command.Command.GetAllDutyNotiNotRead_REQUEST;
import static netty.protocol.command.Command.GetAllFriendInfo_REQUEST;
import static netty.protocol.command.Command.GetAllSD_REQUEST;
import static netty.protocol.command.Command.GetCourseSchOfUID_REQUEST;
import static netty.protocol.command.Command.GetDongtaiByDTID_REQUEST;
import static netty.protocol.command.Command.GetDongtaiICByDTID_REQUEST;
import static netty.protocol.command.Command.GetDongtaiUICByDTID_REQUEST;
import static netty.protocol.command.Command.GetFriendIDOnline_REQUEST;
import static netty.protocol.command.Command.GetFriendInfoByID_REQUEST;
import static netty.protocol.command.Command.GetFriendListUIcByPh_Request;
import static netty.protocol.command.Command.GetGroupAllUser_REQUEST;
import static netty.protocol.command.Command.GetGroupChatMsgNotRead_REQUEST;
import static netty.protocol.command.Command.GetGroupICOfSearchAddGroup_Request;
import static netty.protocol.command.Command.GetGroupIcByGid_Request;
import static netty.protocol.command.Command.GetGroupInfoByGid_REQUEST;
import static netty.protocol.command.Command.GetGroupUserIconByPh_REQUEST;
import static netty.protocol.command.Command.GetGroupsInfoOfUser_REQUEST;
import static netty.protocol.command.Command.GetIndexICOfPh_REQUEST;
import static netty.protocol.command.Command.GetIndexInfoOfPh_REQUEST;
import static netty.protocol.command.Command.GetNewDongtaiIDs_REQUEST;
import static netty.protocol.command.Command.GetNotiIconOfGroup_Request;
import static netty.protocol.command.Command.GetNotiIconOfUser_Request;
import static netty.protocol.command.Command.GetOldDongtaiIDs_REQUEST;
import static netty.protocol.command.Command.GetPerIcon_REQUEST;
import static netty.protocol.command.Command.GetPersonalInfo_REQUEST;
import static netty.protocol.command.Command.GetRequestFriendOrGroup_REQUEST;
import static netty.protocol.command.Command.GetSDOfGid_REQUEST;
import static netty.protocol.command.Command.GetScoreOfUID_REQUEST;
import static netty.protocol.command.Command.GetSingleChatMsgNotRead_REQUEST;
import static netty.protocol.command.Command.GetUDtByDTID_REQUEST;
import static netty.protocol.command.Command.GetUDtICByDTICID_REQUEST;
import static netty.protocol.command.Command.GetUICOfSearchAddUser_Request;
import static netty.protocol.command.Command.GetUIcOfAgreeYourFriend_Request;
import static netty.protocol.command.Command.GetUNDtIDs_REQUEST;
import static netty.protocol.command.Command.GetUsODtIDs_REQUEST;
import static netty.protocol.command.Command.GetUserIcOfAddGroup_REQUEST;
import static netty.protocol.command.Command.GetUserIcOfAddMeAsFriend_REQUEST;
import static netty.protocol.command.Command.GetUserIcOfPh_REQUEST;
import static netty.protocol.command.Command.GroupAdmiReciveExitGroup_REQUEST;
import static netty.protocol.command.Command.LOGOUT_REQUEST;
import static netty.protocol.command.Command.LoadCourseOfCorp_REQUEST;
import static netty.protocol.command.Command.ReadAddGroupResult_REQUEST;
import static netty.protocol.command.Command.ReadDutyNoti_REQUEST;
import static netty.protocol.command.Command.ReadGroupChatMsg_REQUEST;
import static netty.protocol.command.Command.ReadSingleChatMsg_REQUEST;
import static netty.protocol.command.Command.ReciveFDeleteMe_REQUEST;
import static netty.protocol.command.Command.SchduleArrangement_REQUEST;
import static netty.protocol.command.Command.SearchAddGroup_REQUEST;
import static netty.protocol.command.Command.SearchAddUser_REQUEST;
import static netty.protocol.command.Command.SearchEmptyCourse_REQUEST;
import static netty.protocol.command.Command.SendGroupChatFile_REQUEST;
import static netty.protocol.command.Command.SendGroupChatText_REQUEST;
import static netty.protocol.command.Command.SendSingleChatFile_REQUEST;
import static netty.protocol.command.Command.SendSingleChatText_REQUEST;
import static netty.protocol.command.Command.UPPASSWORD_REQUEST;
import static netty.protocol.command.Command.UpFriendRemark_REQUEST;
import static netty.protocol.command.Command.UpGroupIcon_Request;
import static netty.protocol.command.Command.UpGroupPart_REQUEST;
import static netty.protocol.command.Command.UpGroupRemark_REQUEST;
import static netty.protocol.command.Command.UpPeronalInfo_REQUEST;
import static netty.protocol.command.Command.UpPersonalIcon_REQUEST;
import static netty.protocol.command.Command.UpRequestMsgState_REQUEST;

import java.util.HashMap;
import java.util.Map;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.protocol.Packet;
import netty.server.chatHandler.GetGroupChatMsgNotReadRequestHandler;
import netty.server.chatHandler.GetSingleChatMsgNotReadRequestHandler;
import netty.server.chatHandler.ReadGroupChatMsgRequestHandler;
import netty.server.chatHandler.ReadSingleChatMsgRequestHandler;
import netty.server.chatHandler.SendGroupChatFileRequestHandler;
import netty.server.chatHandler.SendGroupChatTextRequestHandler;
import netty.server.chatHandler.SendSingleChatFileRequestHandler;
import netty.server.chatHandler.SendSingleChatTextRequestHandler;
import netty.server.corprationHandler.AddCorpPartRequestHandler;
import netty.server.corprationHandler.AlterCorpPartRequestHandler;
import netty.server.corprationHandler.AlterCorpPosRequestHandler;
import netty.server.corprationHandler.AlterCorpYearTermRequestHandler;
import netty.server.corprationHandler.AppointCorpPosRequestHandler;
import netty.server.corprationHandler.CorpLoadCourseRsRequestHandler;
import netty.server.corprationHandler.CreateCorprationRequestHandler;
import netty.server.corprationHandler.DeleteCorpPartRequestHandler;
import netty.server.corprationHandler.GetAllDutyNotiNotReadRequestHandler;
import netty.server.corprationHandler.GetAllSDRequestHandler;
import netty.server.corprationHandler.GetSDOfGidRequestHandler;
import netty.server.corprationHandler.LoadCourseOfCorpRequestHandler;
import netty.server.corprationHandler.ReadDutyNotiRequestHandler;
import netty.server.corprationHandler.SchduleArrangementRequestHandler;
import netty.server.corprationHandler.SearchEmptyCourseRequestHandler;
import netty.server.corprationHandler.UpGroupPartRequestHandler;
import netty.server.dongtaiHandler.AddDongTaiImageRequestHandler;
import netty.server.dongtaiHandler.AddDongtaiRequestHandler;
import netty.server.dongtaiHandler.GetDongtaiByDTIDRequestHandler;
import netty.server.dongtaiHandler.GetDongtaiICByDTID_RequestHandler;
import netty.server.dongtaiHandler.GetDongtaiUICByDTID_RequestHandler;
import netty.server.dongtaiHandler.GetNewDongtaiIDsReqeustHandler;
import netty.server.dongtaiHandler.GetOldDongtaiIDsRequestHandler;
import netty.server.dongtaiHandler.GetUDtByDTIDRequestHandler;
import netty.server.dongtaiHandler.GetUDtICByDTICID_RequestHandler;
import netty.server.dongtaiHandler.GetUNDtIDsRequestHandler;
import netty.server.dongtaiHandler.GetUsODtIDsRequestHandler;
import netty.server.eduSysHandler.GetCourseSchOfUIDRequestHandler;
import netty.server.eduSysHandler.GetScoreOfUIDRequestHandler;
import netty.server.eduSysHandler.SearchEmptyClassroomRequestHandler;
import netty.server.friendGroupOperHandler.AddFriendRequestHandler;
import netty.server.friendGroupOperHandler.AddFriendResultRequestHandler;
import netty.server.friendGroupOperHandler.AddGroupRequestHandler;
import netty.server.friendGroupOperHandler.AddGroupReturnRequestHandler;
import netty.server.friendGroupOperHandler.AddGroupToGroupListRequestHandler;
import netty.server.friendGroupOperHandler.AddMeFriendReturnRequestHandler;
import netty.server.friendGroupOperHandler.AddUserToFriendListRequestHandler;
import netty.server.friendGroupOperHandler.CreateGroupRequestHandler;
import netty.server.friendGroupOperHandler.DeleteFriendRequestHandler;
import netty.server.friendGroupOperHandler.ExitGroupRequestHandler;
import netty.server.friendGroupOperHandler.GetFriendListUIcByPhRequestHandler;
import netty.server.friendGroupOperHandler.GetGroupAllUserRequestHandler;
import netty.server.friendGroupOperHandler.GetGroupICOfSearchAddGroupRequestHandler;
import netty.server.friendGroupOperHandler.GetGroupIcByGidRequestHandler;
import netty.server.friendGroupOperHandler.GetGroupUserIconByPhRequestHandler;
import netty.server.friendGroupOperHandler.GetRequestFriendOrGroupRequestHandler;
import netty.server.friendGroupOperHandler.GetUICOfSearchAddUserRequestHandler;
import netty.server.friendGroupOperHandler.GetUIcOfAgreeYourFriendRequestHandler;
import netty.server.friendGroupOperHandler.GetUserIcOfAddGroupRequestHandler;
import netty.server.friendGroupOperHandler.GetUserIcOfAddMeAsFriendRequestHandler;
import netty.server.friendGroupOperHandler.GroupAdmiReciveExitGroupRequestHandler;
import netty.server.friendGroupOperHandler.ReadAddGroupResultRequestHandler;
import netty.server.friendGroupOperHandler.ReciveFDeleteMeRequestHandler;
import netty.server.friendGroupOperHandler.SearchAddGroupRequestHandler;
import netty.server.friendGroupOperHandler.SearchAddUserRequestHandler;
import netty.server.friendGroupOperHandler.UpFriendRemarkRequestHandler;
import netty.server.friendGroupOperHandler.UpGroupIconRequestHandler;
import netty.server.friendGroupOperHandler.UpGroupRemarkRequestHandler;
import netty.server.friendGroupOperHandler.UpRequestMsgStateRequestHandler;
import netty.server.notificationHandler.GetNotiIconOfGroupRequestHandler;
import netty.server.notificationHandler.GetNotiIconOfUserRequestHandler;
import netty.server.userOtherInfoHandler.FreshAllFriendInfoRequestHandler;
import netty.server.userOtherInfoHandler.GetAllFriendInfoRequestHandler;
import netty.server.userOtherInfoHandler.GetFriendIDOnlineRequestHandler;
import netty.server.userOtherInfoHandler.GetGroupInfoByGidRequestHandler;
import netty.server.userOtherInfoHandler.GetGroupsInfoOfUserRequestHandler;
import netty.server.userOtherInfoHandler.GetIndexICOfPhRequestHandler;
import netty.server.userOtherInfoHandler.GetIndexInfoOfPhRequestHandler;
import netty.server.userOtherInfoHandler.GetUserIcOfPhResquestHandler;
import netty.server.userPersonalInfoHandler.GetFriendInfoByIDRequestHandler;
import netty.server.userPersonalInfoHandler.GetPerIconRequestHandler;
import netty.server.userPersonalInfoHandler.GetPersonalInfoRequestHandler;
import netty.server.userPersonalInfoHandler.UpPasswordRequestHandler;
import netty.server.userPersonalInfoHandler.UpPersonalIconRequestHandler;
import netty.server.userPersonalInfoHandler.UpPersonalInfoRequestHandler;


@ChannelHandler.Sharable
public class IMHandler extends SimpleChannelInboundHandler<Packet> {
    public static final IMHandler INSTANCE = new IMHandler();

    private Map<Integer, SimpleChannelInboundHandler<? extends Packet>> handlerMap;

    private IMHandler() {
        handlerMap = new HashMap<>();
        
        handlerMap.put(LOGOUT_REQUEST, LogoutRequestHandler.INSTANCE);
        handlerMap.put(UPPASSWORD_REQUEST, UpPasswordRequestHandler.INSTANCE);
       
        handlerMap.put(SendGroupChatText_REQUEST, SendGroupChatTextRequestHandler.INSTANCE);
        handlerMap.put(SendGroupChatFile_REQUEST, SendGroupChatFileRequestHandler.INSTANCE);
        handlerMap.put(ReadGroupChatMsg_REQUEST, ReadGroupChatMsgRequestHandler.INSTANCE);
        handlerMap.put(SendSingleChatText_REQUEST, SendSingleChatTextRequestHandler.INSTANCE);
    
        handlerMap.put(SendSingleChatFile_REQUEST, SendSingleChatFileRequestHandler.INSTANCE);
        handlerMap.put(ReadSingleChatMsg_REQUEST, ReadSingleChatMsgRequestHandler.INSTANCE);
        handlerMap.put(GetFriendInfoByID_REQUEST, GetFriendInfoByIDRequestHandler.INSTANCE);
        handlerMap.put(GetGroupsInfoOfUser_REQUEST, GetGroupsInfoOfUserRequestHandler.INSTANCE);
        handlerMap.put(GetAllFriendInfo_REQUEST, GetAllFriendInfoRequestHandler.INSTANCE);
        handlerMap.put(FreshAllFriendInfo_REQUEST, FreshAllFriendInfoRequestHandler.INSTANCE);
//        handlerMap.put(GetFriendIconOfUID_REQUEST, GetFriendIconOfUID.INSTANCE);
        
//        handlerMap.put(FreshNotification_REQUEST, FreshNotificationRequestHandler.INSTANCE);
        handlerMap.put(GetFriendIDOnline_REQUEST, GetFriendIDOnlineRequestHandler.INSTANCE);
        handlerMap.put(DeleteFriend_REQUEST, DeleteFriendRequestHandler.INSTANCE);
        handlerMap.put(ReciveFDeleteMe_REQUEST, ReciveFDeleteMeRequestHandler.INSTANCE);
        handlerMap.put(ExitGroup_REQUEST, ExitGroupRequestHandler.INSTANCE);
        handlerMap.put(GroupAdmiReciveExitGroup_REQUEST, GroupAdmiReciveExitGroupRequestHandler.INSTANCE);
        handlerMap.put(UpRequestMsgState_REQUEST, UpRequestMsgStateRequestHandler.INSTANCE);
        
        handlerMap.put(AddFriend_REQUEST, AddFriendRequestHandler.INSTANCE);
        handlerMap.put(AddMeFriendReturn_REQUEST, AddMeFriendReturnRequestHandler.INSTANCE);
        handlerMap.put(AddFriendResult_REQUEST, AddFriendResultRequestHandler.INSTANCE);
        handlerMap.put(AddGroup_REQUEST, AddGroupRequestHandler.INSTANCE);
        handlerMap.put(AddGroupReturn_REQUEST, AddGroupReturnRequestHandler.INSTANCE);
        handlerMap.put(ReadAddGroupResult_REQUEST, ReadAddGroupResultRequestHandler.INSTANCE);
        handlerMap.put(GetRequestFriendOrGroup_REQUEST, GetRequestFriendOrGroupRequestHandler.INSTANCE);
        
        handlerMap.put(GetSingleChatMsgNotRead_REQUEST, GetSingleChatMsgNotReadRequestHandler.INSTANCE);
        handlerMap.put(GetGroupChatMsgNotRead_REQUEST, GetGroupChatMsgNotReadRequestHandler.INSTANCE);
        handlerMap.put(GetPersonalInfo_REQUEST, GetPersonalInfoRequestHandler.INSTANCE);
        handlerMap.put(SearchAddUser_REQUEST, SearchAddUserRequestHandler.INSTANCE);
        handlerMap.put(SearchAddGroup_REQUEST, SearchAddGroupRequestHandler.INSTANCE);
        handlerMap.put(UpPersonalIcon_REQUEST, UpPersonalIconRequestHandler.INSTANCE);
        handlerMap.put(GetPerIcon_REQUEST, GetPerIconRequestHandler.INSTANCE);
        
        handlerMap.put(UpPeronalInfo_REQUEST, UpPersonalInfoRequestHandler.INSTANCE);
        handlerMap.put(UpFriendRemark_REQUEST, UpFriendRemarkRequestHandler.INSTANCE);
        handlerMap.put(AddUserToFriendList_REQUEST, AddUserToFriendListRequestHandler.INSTANCE);
        handlerMap.put(UpGroupRemark_REQUEST, UpGroupRemarkRequestHandler.INSTANCE);
        handlerMap.put(UpGroupPart_REQUEST, UpGroupPartRequestHandler.INSTANCE);
        handlerMap.put(GetGroupAllUser_REQUEST, GetGroupAllUserRequestHandler.INSTANCE);
        handlerMap.put(GetGroupUserIconByPh_REQUEST, GetGroupUserIconByPhRequestHandler.INSTANCE);
        
        handlerMap.put(CreateCorpration_REQUEST, CreateCorprationRequestHandler.INSTANCE);
        handlerMap.put(CreateGroup_REQUEST, CreateGroupRequestHandler.INSTANCE);
        handlerMap.put(AddGroupToGroupList_REQUEST, AddGroupToGroupListRequestHandler.INSTANCE);
        handlerMap.put(GetCourseSchOfUID_REQUEST, GetCourseSchOfUIDRequestHandler.INSTANCE);
        handlerMap.put(GetScoreOfUID_REQUEST, GetScoreOfUIDRequestHandler.INSTANCE);
        handlerMap.put(LoadCourseOfCorp_REQUEST, LoadCourseOfCorpRequestHandler.INSTANCE);
        handlerMap.put(SchduleArrangement_REQUEST, SchduleArrangementRequestHandler.INSTANCE);
        
        handlerMap.put(GetSDOfGid_REQUEST, GetSDOfGidRequestHandler.INSTANCE);
        handlerMap.put(GetAllSD_REQUEST, GetAllSDRequestHandler.INSTANCE);
        handlerMap.put(GetAllDutyNotiNotRead_REQUEST, GetAllDutyNotiNotReadRequestHandler.INSTANCE);
        handlerMap.put(ReadDutyNoti_REQUEST, ReadDutyNotiRequestHandler.INSTANCE);
        handlerMap.put(SearchEmptyCourse_REQUEST, SearchEmptyCourseRequestHandler.INSTANCE);
        handlerMap.put(CorpLoadCourseRs_REQUEST, CorpLoadCourseRsRequestHandler.INSTANCE);
        handlerMap.put(AlterCorpYearTerm_REQUEST, AlterCorpYearTermRequestHandler.INSTANCE);
        
        handlerMap.put(AddCorApPart_REQUEST, AddCorpPartRequestHandler.INSTANCE);
        handlerMap.put(DeleteCorpPart_REQUEST, DeleteCorpPartRequestHandler.INSTANCE);
        handlerMap.put(AlterCorpPart_REQUEST, AlterCorpPartRequestHandler.INSTANCE);
        handlerMap.put(AlterCorpPos_REQUEST, AlterCorpPosRequestHandler.INSTANCE);
       
        handlerMap.put(GetUDtByDTID_REQUEST, GetUDtByDTIDRequestHandler.INSTANCE);
        handlerMap.put( GetUDtICByDTICID_REQUEST,  GetUDtICByDTICID_RequestHandler.INSTANCE);

        handlerMap.put(GetUNDtIDs_REQUEST, GetUNDtIDsRequestHandler.INSTANCE);
        handlerMap.put(GetUsODtIDs_REQUEST, GetUsODtIDsRequestHandler.INSTANCE);
        handlerMap.put(GetDongtaiByDTID_REQUEST, GetDongtaiByDTIDRequestHandler.INSTANCE);
        handlerMap.put(GetDongtaiUICByDTID_REQUEST, GetDongtaiUICByDTID_RequestHandler.INSTANCE);
        handlerMap.put(GetDongtaiICByDTID_REQUEST, GetDongtaiICByDTID_RequestHandler.INSTANCE);
        
        
        handlerMap.put(GetNewDongtaiIDs_REQUEST, GetNewDongtaiIDsReqeustHandler.INSTANCE);
        handlerMap.put(GetOldDongtaiIDs_REQUEST, GetOldDongtaiIDsRequestHandler.INSTANCE);
        handlerMap.put(AddDongtai_REQUEST, AddDongtaiRequestHandler.INSTANCE);
        handlerMap.put(AddDongTaiImage_REQUEST, AddDongTaiImageRequestHandler.INSTANCE);
       
        //进入某人的个人页面时  获得其基本信息
        handlerMap.put(GetIndexInfoOfPh_REQUEST, GetIndexInfoOfPhRequestHandler.INSTANCE);
      //进入某人的个人页面时  获得其头像
        handlerMap.put(GetIndexICOfPh_REQUEST, GetIndexICOfPhRequestHandler.INSTANCE);
        //社团组织任命职位
        handlerMap.put(AppointCorpPos_REQUEST, AppointCorpPosRequestHandler.INSTANCE);
       
        //	添加好友  被添加方要获得对方的头像
        handlerMap.put(GetUserIcOfAddMeAsFriend_REQUEST, GetUserIcOfAddMeAsFriendRequestHandler.INSTANCE);
        
        //对方同意了你的好友请求    获得对方的头像
        handlerMap.put(GetUIcOfAgreeYourFriend_Request, GetUIcOfAgreeYourFriendRequestHandler.INSTANCE);
    
        //添加好友时    首先查询     获得对方的头像
        handlerMap.put(GetUICOfSearchAddUser_Request, GetUICOfSearchAddUserRequestHandler.INSTANCE);
       
        //更改群头像
        handlerMap.put(UpGroupIcon_Request, UpGroupIconRequestHandler.INSTANCE);
        
        //获得某群头像
        handlerMap.put(GetGroupIcByGid_Request, GetGroupIcByGidRequestHandler.INSTANCE);
        
        //获得某群头像    客户端加载消息通知时  若本机没有头像  那么向服务器请求
        handlerMap.put(GetNotiIconOfGroup_Request, GetNotiIconOfGroupRequestHandler.INSTANCE);
        
        //获得某群头像    客户端加载消息通知时  若本机没有头像  那么向服务器请求
        handlerMap.put(GetNotiIconOfUser_Request, GetNotiIconOfUserRequestHandler.INSTANCE);
        
        //获得某群头像    客户端加载消息通知时  若本机没有头像  那么向服务器请求
        handlerMap.put(GetFriendListUIcByPh_Request, GetFriendListUIcByPhRequestHandler.INSTANCE);
        
        //添加群时    首先查询     获得群的头像
        handlerMap.put(GetGroupICOfSearchAddGroup_Request, GetGroupICOfSearchAddGroupRequestHandler.INSTANCE);
        //添加群时    首先查询     获得群的头像
        handlerMap.put(GetUserIcOfAddGroup_REQUEST, GetUserIcOfAddGroupRequestHandler.INSTANCE);
        // 获得某用户头像
        handlerMap.put(GetUserIcOfPh_REQUEST, GetUserIcOfPhResquestHandler.INSTANCE);
        
        // 获得用户的某群的信息   一般用在更新某群信息时
        handlerMap.put(GetGroupInfoByGid_REQUEST, GetGroupInfoByGidRequestHandler.INSTANCE);
        
        // 查询空教室
        handlerMap.put(SearchEmptyClassroom_REQUEST, SearchEmptyClassroomRequestHandler.INSTANCE);
        
        
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet packet) throws Exception {
        handlerMap.get(packet.getCommand()).channelRead(ctx, packet);
    }
}
