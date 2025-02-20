package com.github.shuaidd.client;

import com.github.shuaidd.client.config.WeChatConfiguration;
import com.github.shuaidd.response.*;
import com.github.shuaidd.response.addressbook.*;
import com.github.shuaidd.response.application.ApplicationButtonResponse;
import com.github.shuaidd.response.application.WeChatApplicationResponse;
import com.github.shuaidd.response.auth.AuthenticationResponse;
import com.github.shuaidd.response.externalcontact.*;
import com.github.shuaidd.response.kf.*;
import com.github.shuaidd.response.message.CreateAppChatResponse;
import com.github.shuaidd.response.message.SearchAppChatResponse;
import com.github.shuaidd.response.message.SendMessageResponse;
import com.github.shuaidd.response.oa.*;
import com.github.shuaidd.response.tool.*;
import com.github.shuaidd.resquest.*;
import com.github.shuaidd.resquest.addressbook.*;
import com.github.shuaidd.resquest.application.ApplicationButtonRequest;
import com.github.shuaidd.resquest.application.ApplicationSettingReuqest;
import com.github.shuaidd.resquest.externalcontact.*;
import com.github.shuaidd.resquest.kf.*;
import com.github.shuaidd.resquest.message.CreateAppChatRequest;
import com.github.shuaidd.resquest.message.SendAppChatRequest;
import com.github.shuaidd.resquest.message.SendMessageRequest;
import com.github.shuaidd.resquest.message.UpdateAppChatRequest;
import com.github.shuaidd.resquest.oa.*;
import com.github.shuaidd.resquest.tool.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 描述 enjoy your life
 *
 * @author ddshuai
 * date 2019-04-03 15:50
 **/
@SuppressWarnings("all")
@FeignClient(name = "wechat", url = "${qywx.url:https://qyapi.weixin.qq.com}", path = "${qywx.public-path:cgi-bin}", configuration = WeChatConfiguration.class)
public interface WeChatClient {

    String HEAD_KEY = "app";
    String HEAD = HEAD_KEY + "={app}";
    String ACCESS_TOKEN = "access_token";
    String GET_TOKEN = "/gettoken";

    /**
     * 获取应用 access token
     *
     * @param corpId 企业号
     * @param secret 密匙
     * @return AccessTokenResponse
     */
    @GetMapping("gettoken")
    AccessTokenResponse getAccessToken(@RequestParam("corpid") String corpId, @RequestParam("corpsecret") String secret);

    /**
     * 创建成员
     *
     * @param request 请求参数
     * @param app     应用
     * @return BaseResponse
     */
    @PostMapping(value = "user/create", headers = HEAD)
    BaseResponse createUser(CreateUserRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 读取成员信息
     *
     * @param app    应用名
     * @param userId 成员编号
     * @return WeChatUserResponse
     */
    @GetMapping(value = "user/get", headers = HEAD)
    WeChatUserResponse getUser(@RequestParam("userid") String userId, @RequestParam(HEAD_KEY) String app);

    /**
     * 更新成员信息【根据userID更新，必传】
     *
     * @param app     应用名
     * @param request 更新请求参数
     * @return BaseResponse
     */
    @PostMapping(value = "user/update", headers = HEAD)
    BaseResponse updateUser(UpdateUserRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 删除成员
     *
     * @param userId 成员编号
     * @param app    应用
     * @return BaseResponse
     */
    @GetMapping(value = "user/delete", headers = HEAD)
    BaseResponse deleteUser(@RequestParam("userid") String userId, @RequestParam(HEAD_KEY) String app);

    /**
     * 批量删除成员
     *
     * @param request 请求体
     * @param app     应用
     * @return BaseResponse
     */
    @PostMapping(value = "user/batchdelete", headers = HEAD)
    BaseResponse batchDelete(BatchDeleteUserRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 获取部门成员[应用须拥有指定部门的查看权限。]
     *
     * @param app          应用名
     * @param departmentId 获取的部门id
     * @param fetchChild   1/0：是否递归获取子部门下面的成员
     * @return DepartmentUserResponse
     */
    @GetMapping(value = "user/simplelist", headers = HEAD)
    DepartmentUserResponse getDepartmentUser(@RequestParam("department_id") Integer departmentId, @RequestParam("fetch_child") Integer fetchChild, @RequestParam(HEAD_KEY) String app);

    /**
     * 获取部门成员详情[应用须拥有指定部门的查看权限。]
     *
     * @param app          应用名
     * @param departmentId 获取的部门id
     * @param fetchChild   1/0：是否递归获取子部门下面的成员
     * @return DepartmentUserResponse
     */
    @GetMapping(value = "user/list", headers = HEAD)
    DepartmentUserResponse getDepartmentUserDetail(@RequestParam("department_id") Integer departmentId, @RequestParam("fetch_child") Integer fetchChild, @RequestParam(HEAD_KEY) String app);

    /**
     * userid转openid
     *
     * @param request 请求体
     * @param app     应用名
     * @return ConvertUserIdOpenIdResponse
     */
    @PostMapping(value = "user/convert_to_openid", headers = HEAD)
    ConvertUserIdOpenIdResponse convertToOpenId(ConvertUserIdOpenIdRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * openid转userid
     *
     * @param request 请求体
     * @param app     应用名
     * @return ConvertUserIdOpenIdResponse
     */
    @PostMapping(value = "user/convert_to_userid", headers = HEAD)
    ConvertUserIdOpenIdResponse convertToUserId(ConvertUserIdOpenIdRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 二次验证
     *
     * @param userId 用户编号
     * @param app    应用名
     * @return BaseResponse
     */
    @GetMapping(value = "user/authsucc", headers = HEAD)
    BaseResponse authSucc(@RequestParam("userid") String userId, @RequestParam(HEAD_KEY) String app);

    /**
     * 邀请成员
     *
     * @param request 请求体
     * @param app     应用名
     * @return BaseResponse
     */
    @PostMapping(value = "batch/invite", headers = HEAD)
    BaseResponse invite(InviteUserRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 创建部门
     *
     * @param app     应用
     * @param request 请求体
     * @return CreateDepartmentResponse
     */
    @PostMapping(value = "department/create", headers = HEAD)
    CreateDepartmentResponse createDepartment(DepartmentRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 更新部门
     *
     * @param request 请求体
     * @param app     应用名
     * @return BaseResponse
     */
    @PostMapping(value = "department/update", headers = HEAD)
    BaseResponse updateDepartment(DepartmentRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 删除部门【注：不能删除根部门；不能删除含有子部门、成员的部门】
     *
     * @param id  企业微信部门id
     * @param app 应用名
     * @return BaseResponse
     */
    @GetMapping(value = "department/delete", headers = HEAD)
    BaseResponse deleteDepartment(@RequestParam(value = "id", required = false) Integer id, @RequestParam(HEAD_KEY) String app);

    /**
     * 权限说明：
     * 只能拉取token对应的应用的权限范围内的部门列表
     * <p>
     * 获取部门列表【获取指定部门及其下的子部门。 如果不填，默认获取全量组织架构】
     *
     * @param id  部门id
     * @param app 应用名
     * @return DepartmentListResponse
     */
    @GetMapping(value = "department/list", headers = HEAD)
    DepartmentListResponse departmentList(@RequestParam(value = "id", required = false) Integer id, @RequestParam(HEAD_KEY) String app);

    /**
     * 创建标签【创建的标签属于该应用，只有该应用才可以增删成员】
     * 注意，标签总数不能超过3000个。
     *
     * @param request 请求体
     * @param app     应用名
     * @return CreateTagResponse
     */
    @PostMapping(value = "tag/create", headers = HEAD)
    CreateTagResponse createTag(TagRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 权限说明：
     * 调用者必须是指定标签的创建者
     * <p>
     * 更新标签名字【长度限制为32个字（汉字或英文字母），标签不可与其他标签重名】
     *
     * @param request 请求体
     * @param app     应用名
     * @return BaseResponse
     */
    @PostMapping(value = "tag/update", headers = HEAD)
    BaseResponse updateTag(TagRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 删除标签
     *
     * @param id  标签ID
     * @param app 应用名
     * @return BaseResponse
     */
    @GetMapping(value = "tag/delete", headers = HEAD)
    BaseResponse deleteTag(@RequestParam("tagid") Integer id, @RequestParam(HEAD_KEY) String app);

    /**
     * 获取标签成员
     *
     * @param id  标签id
     * @param app 应用名
     * @return QueryTagUserResponse
     */
    @GetMapping(value = "tag/get", headers = HEAD)
    QueryTagUserResponse getTagUser(@RequestParam("tagid") Integer id, @RequestParam(HEAD_KEY) String app);

    /**
     * 增加标签成员
     *
     * @param request 请求体
     * @param app     应用名
     * @return TagUserResponse
     */
    @PostMapping(value = "tag/addtagusers", headers = HEAD)
    TagUserResponse addTagUsers(TagUserRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 删除标签成员
     *
     * @param request 请求体
     * @param app     应用名
     * @return TagUserResponse
     */
    @PostMapping(value = "tag/deltagusers", headers = HEAD)
    TagUserResponse deleteTagUsers(TagUserRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 获取标签列表
     *
     * @param app 应用名
     * @return TagListResponse
     */
    @GetMapping(value = "tag/list", headers = HEAD)
    TagListResponse getTagList(@RequestParam(HEAD_KEY) String app);

    /**
     * 注意事项：
     * <p>
     * 模板中的部门需填写部门ID，多个部门用分号分隔，部门ID必须为数字，根部门的部门id默认为1
     * 文件中存在、通讯录中也存在的成员，更新成员在文件中指定的字段值
     * 文件中存在、通讯录中不存在的成员，执行添加操作
     * 通讯录中存在、文件中不存在的成员，保持不变
     * 成员字段更新规则：可自行添加扩展字段。文件中有指定的字段，以指定的字段值为准；文件中没指定的字段，不更新
     * <p>
     *
     * 增量更新成员
     *
     * @param request 请求体
     * @param app     应用名
     * @return AsyncJobResponse
     */
    @PostMapping(value = "batch/syncuser", headers = HEAD)
    AsyncJobResponse asyncBatchUpdateUser(AsyncJobRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 注意事项：
     * <p>
     * 模板中的部门需填写部门ID，多个部门用分号分隔，部门ID必须为数字，根部门的部门id默认为1
     * 文件中存在、通讯录中也存在的成员，完全以文件为准
     * 文件中存在、通讯录中不存在的成员，执行添加操作
     * 通讯录中存在、文件中不存在的成员，执行删除操作。出于安全考虑，下面两种情形系统将中止导入并返回相应的错误码。
     * 需要删除的成员多于50人，且多于现有人数的20%以上
     * 需要删除的成员少于50人，且多于现有人数的80%以上
     * 成员字段更新规则：可自行添加扩展字段。文件中有指定的字段，以指定的字段值为准；文件中没指定的字段，不更新
     * <p>
     * 全量覆盖成员
     *
     * @param request 请求体
     * @param app     应用名
     * @return AsyncJobResponse
     */
    @PostMapping(value = "batch/replaceuser", headers = HEAD)
    AsyncJobResponse fullCoverUser(AsyncJobRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 注意事项：
     * <p>
     * 文件中存在、通讯录中也存在的部门，执行修改操作
     * 文件中存在、通讯录中不存在的部门，执行添加操作
     * 文件中不存在、通讯录中存在的部门，当部门下没有任何成员或子部门时，执行删除操作
     * 文件中不存在、通讯录中存在的部门，当部门下仍有成员或子部门时，暂时不会删除，当下次导入成员把人从部门移出后自动删除
     * CSV文件中，部门名称、部门ID、父部门ID为必填字段，部门ID必须为数字，根部门的部门id默认为1；排序为可选字段，置空或填0不修改排序, order值大的排序靠前。
     * <p>
     * 全量覆盖部门
     *
     * @param request 请求体
     * @param app     应用名
     * @return AsyncJobResponse
     */
    @PostMapping(value = "batch/replaceparty", headers = HEAD)
    AsyncJobResponse fullCoverDepartment(AsyncJobRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 获取异步任务结果
     *
     * @param jobId 任务id
     * @param app   应用名
     * @return AsyncJobResultResponse
     */
    @GetMapping(value = "batch/getresult", headers = HEAD)
    AsyncJobResultResponse jobResult(@RequestParam("jobid") String jobId, @RequestParam(HEAD_KEY) String app);

    /**
     * 获取访问用户身份
     *
     * @param code 授权码
     * @param app  应用名
     * @return AuthenticationResponse
     */
    @GetMapping(value = "user/getuserinfo", headers = HEAD)
    AuthenticationResponse getAuthentication(@RequestParam("code") String code, @RequestParam(HEAD_KEY) String app);

    /**
     * 获取应用
     *
     * @param agentId 应用编号
     * @param app     应用名
     * @return WeChatApplicationResponse
     */
    @GetMapping(value = "agent/get", headers = HEAD)
    WeChatApplicationResponse getApplication(@RequestParam("agentid") String agentId, @RequestParam(HEAD_KEY) String app);

    /**
     * 设置应用
     *
     * @param reuqest 请求
     * @param app     应用名
     * @return BaseResponse
     */
    @PostMapping(value = "agent/set", headers = HEAD)
    BaseResponse applicationSetting(ApplicationSettingReuqest reuqest, @RequestParam(HEAD_KEY) String app);

    /**
     * 创建菜单
     *
     * @param request 请求体
     * @param agentid 应用编号
     * @param app     应用名
     * @return BaseResponse
     */
    @PostMapping(value = "menu/create", headers = HEAD)
    BaseResponse createApplicationButton(ApplicationButtonRequest request, @RequestParam("agentid") String agentid, @RequestParam(HEAD_KEY) String app);

    /**
     * 获取菜单
     *
     * @param agentid 应用编号
     * @param app     应用名
     * @return ApplicationButtonResponse
     */
    @GetMapping(value = "menu/get", headers = HEAD)
    ApplicationButtonResponse getApplicationButtons(@RequestParam("agentid") String agentid, @RequestParam(HEAD_KEY) String app);

    /**
     * 删除菜单
     *
     * @param agentid 应用编号
     * @param app     应用名
     * @return BaseResponse
     */
    @GetMapping(value = "menu/delete", headers = HEAD)
    BaseResponse deleteApplicationButtons(@RequestParam("agentid") String agentid, @RequestParam(HEAD_KEY) String app);

    /**
     * 发送消息
     *
     * @param request 请求体
     * @param app     应用名
     * @return SendMessageResponse
     */
    @PostMapping(value = "message/send", headers = HEAD)
    SendMessageResponse sendMessage(SendMessageRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 创建群聊会话
     *
     * @param request 请求体
     * @param app     应用名
     * @return CreateAppChatResponse
     */
    @PostMapping(value = "appchat/create", headers = HEAD)
    CreateAppChatResponse createAppChat(CreateAppChatRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 修改群聊会话
     *
     * @param request 请求体
     * @param app     应用名
     * @return BaseResponse
     */
    @PostMapping(value = "appchat/update", headers = HEAD)
    BaseResponse updateAppChat(UpdateAppChatRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 获取群聊会话
     *
     * @param chatId 群聊编号
     * @param app    应用名
     * @return SearchAppChatResponse
     */
    @GetMapping(value = "appchat/get", headers = HEAD)
    SearchAppChatResponse searchAppChat(@RequestParam("chatid") String chatId, @RequestParam(HEAD_KEY) String app);

    /**
     * 发送群消息
     *
     * @param request 请求体
     * @param app     应用名
     * @return BaseResponse
     */
    @PostMapping(value = "appchat/send", headers = HEAD)
    BaseResponse sendAppChatMessage(SendAppChatRequest request, @RequestParam(HEAD_KEY) String app);


    /**
     * 获取打卡数据
     * <p>
     * 获取记录时间跨度不超过一个月
     * 用户列表不超过100个。若用户超过100个，请分批获取
     * 有打卡记录即可获取打卡数据，与当前”打卡应用”是否开启无关
     *
     * @param request 请求体
     * @param app     应用名
     * @return CheckInDataResponse
     */
    @PostMapping(value = "checkin/getcheckindata", headers = HEAD)
    CheckInDataResponse getCheckInData(CheckInDataRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 获取打卡规则
     * <p>
     * 用户列表不超过100个，若用户超过100个，请分批获取。
     * 用户在不同日期的规则不一定相同，请按天获取。
     *
     * @param request 请求体
     * @param app     应用名
     * @return CheckInRuleResponse
     */
    @PostMapping(value = "checkin/getcheckinoption", headers = HEAD)
    CheckInRuleResponse getCheckInOption(CheckInRuleRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 获取审批数据
     * <p>
     * 获取审批记录请求参数endtime需要大于startime， 切起始时间跨度不要超过一个月；
     * 一次请求返回的审批记录上限是100条，超过100条记录请使用next_spnum进行分页拉取。
     *
     * @param request 请求体
     * @param app     应用名
     * @return ApprovalDataResponse
     */
    @PostMapping(value = "corp/getapprovaldata", headers = HEAD)
    ApprovalDataResponse getApprovalData(ApprovalDataRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 获取公费电话拨打记录
     *
     * @param request 请求体
     * @param app     应用名
     * @return DialRecordResponse
     */
    @PostMapping(value = "dial/get_dial_record", headers = HEAD)
    DialRecordResponse getDialRecord(DialRecordRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 获取配置了客户联系功能的成员列表
     *
     * @param app 应用名
     * @return FollowUserResponse
     */
    @GetMapping(value = "externalcontact/get_follow_user_list", headers = HEAD)
    FollowUserResponse getFollowUserList(@RequestParam(HEAD_KEY) String app);

    /**
     * 配置客户联系「联系我」方式
     *
     * @param request 请求体
     * @param app     应用名
     * @return AddContactWayResponse
     */
    @PostMapping(value = "externalcontact/add_contact_way", headers = HEAD)
    AddContactWayResponse addContactWay(AddContactWayRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 获取企业已配置的「联系我」方式
     *
     * @param request 请求体
     * @param app     应用名
     * @return ContactWayResponse
     */
    @PostMapping(value = "externalcontact/get_contact_way", headers = HEAD)
    ContactWayResponse getContactWay(GetContactWayRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 更新企业已配置的「联系我」方式
     *
     * @param request 请求体
     * @param app     应用名
     * @return BaseResponse
     */
    @PostMapping(value = "externalcontact/update_contact_way", headers = HEAD)
    BaseResponse updateContactWay(UpdateContactWayRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 删除企业已配置的「联系我」方式
     *
     * @param request 请求体
     * @param app     应用名
     * @return BaseResponse
     */
    @PostMapping(value = "externalcontact/del_contact_way", headers = HEAD)
    BaseResponse deleteContactWay(GetContactWayRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 结束临时会话
     *
     * @param request 请求体
     * @param app     应用名
     * @return BaseResponse
     */
    @PostMapping(value = "externalcontact/close_temp_chat", headers = HEAD)
    BaseResponse closeTempChat(CloseTempChatRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 获取客户列表
     *
     * @param userId 用户编号
     * @param app    应用名
     * @return CustomListResponse
     */
    @GetMapping(value = "externalcontact/list", headers = HEAD)
    CustomListResponse getCustomList(@RequestParam("userid") String userId, @RequestParam(HEAD_KEY) String app);

    /**
     * 获取客户详情
     *
     * @param externalUserId 客户编号
     * @param app            应用名
     * @return ExternalContactResponse
     */
    @GetMapping(value = "externalcontact/get", headers = HEAD)
    ExternalContactResponse getExternalContact(@RequestParam("external_userid") String externalUserId, @RequestParam(HEAD_KEY) String app);

    /**
     * 批量获取客户详情
     *
     * @param request 请求体
     * @param app     应用名
     * @return BatchExternalContactResponse
     */
    @PostMapping(value = "externalcontact/batch/get_by_user", headers = HEAD)
    BatchExternalContactResponse getBatchExternalContact(BatchExternalContactRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 修改客户备注信息
     *
     * @param remarkRequest 请求
     * @param app           应用名
     * @return BaseResponse
     */
    @PostMapping(value = "externalcontact/remark", headers = HEAD)
    BaseResponse updateCustomRemark(UpdateRemarkRequest remarkRequest, @RequestParam(HEAD_KEY) String app);

    /**
     * 获取手机号随机串
     *
     * @param request 请求体
     * @param app     应用名
     * @return MobileHashCodeResponse
     */
    @PostMapping(value = "user/get_mobile_hashcode", headers = HEAD)
    MobileHashCodeResponse getMobileHashcode(MobileHashCodeRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 获取企业标签库
     *
     * @param request 请求体
     * @param app     应用名
     * @return TagGroupResponse
     */
    @PostMapping(value = "externalcontact/get_corp_tag_list", headers = HEAD)
    TagGroupResponse getCorpTagList(TagGroupRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 添加企业客户标签
     *
     * @param request 请求体
     * @param app     应用名
     * @return AddCorpTagResponse
     */
    @PostMapping(value = "externalcontact/add_corp_tag", headers = HEAD)
    AddCorpTagResponse addCorpTag(AddCorpTagRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 编辑企业客户标签
     *
     * @param request 请求体
     * @param app     应用名
     * @return BaseResponse
     */
    @PostMapping(value = "externalcontact/edit_corp_tag", headers = HEAD)
    BaseResponse editCorpTag(EditCorpTagRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 删除企业客户标签
     *
     * @param request 请求体
     * @param app     应用名
     * @return BaseResponse
     */
    @PostMapping(value = "externalcontact/del_corp_tag", headers = HEAD)
    BaseResponse delCorpTag(DelCorpTagRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 编辑客户企业标签
     *
     * @param request 请求体
     * @param app     应用名
     * @return BaseResponse
     */
    @PostMapping(value = "externalcontact/mark_tag", headers = HEAD)
    BaseResponse markTag(MarkTagRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 获取离职成员列表
     *
     * @param request 请求体
     * @param app     应用名
     * @return UnassignedListResponse
     */
    @PostMapping(value = "externalcontact/get_unassigned_list", headers = HEAD)
    UnassignedListResponse unassignedList(PageRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 分配在职或离职成员的客户
     *
     * @param request 请求体
     * @param app     应用名
     * @return BaseResponse
     */
    @PostMapping(value = "externalcontact/transfer", headers = HEAD)
    BaseResponse transfer(TransferRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 查询客户接替结果
     *
     * @param resultRequest 请求
     * @param app           应用名
     * @return TransferResultResponse
     */
    @PostMapping(value = "externalcontact/get_transfer_result", headers = HEAD)
    TransferResultResponse getTransferResult(TransferResultRequest resultRequest, @RequestParam(HEAD_KEY) String app);

    /**
     * 分配离职成员的客户群
     *
     * @param request 请求体
     * @param app     应用名
     * @return FailedChatResponse
     */
    @PostMapping(value = "externalcontact/groupchat/transfer", headers = HEAD)
    FailedChatResponse groupChatTransfer(GroupChatTransferRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 获取客户群列表
     *
     * @param request 请求体
     * @param app     应用名
     * @return GroupChatListResponse
     */
    @PostMapping(value = "externalcontact/groupchat/list", headers = HEAD)
    GroupChatListResponse groupChatList(GroupChatListRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 获取客户群详情
     *
     * @param request 请求体
     * @param app     应用名
     * @return GroupChatDetailResponse
     */
    @PostMapping(value = "externalcontact/groupchat/get", headers = HEAD)
    GroupChatDetailResponse groupChatDetail(GroupChatDetailRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 获取企业全部的发表列表
     *
     * @param request 请求体
     * @param app     应用名
     * @return MomentListResponse
     */
    @PostMapping(value = "externalcontact/get_moment_list", headers = HEAD)
    MomentListResponse getMomentList(MomentListRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 获取客户朋友圈企业发表的列表
     *
     * @param request 请求体
     * @param app     应用名
     * @return MomentTaskResponse
     */
    @PostMapping(value = "externalcontact/get_moment_task", headers = HEAD)
    MomentTaskResponse getMomentTask(MomentTaskRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 获取客户朋友圈发表时选择的可见范围
     *
     * @param request 请求体
     * @param app     应用名
     * @return MomentCustomerListResponse
     */
    @PostMapping(value = "externalcontact/get_moment_customer_list", headers = HEAD)
    MomentCustomerListResponse getMomentCustomerList(MomentCustomerListRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 获取客户朋友圈发表后的可见客户列表
     *
     * @param request 请求体
     * @param app     应用名
     * @return MomentCustomerListResponse
     */
    @PostMapping(value = "externalcontact/get_moment_send_result", headers = HEAD)
    MomentCustomerListResponse getMomentSendResult(MomentCustomerListRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 获取客户朋友圈的互动数据
     *
     * @param request 请求体
     * @param app     应用名
     * @return CommentsResponse
     */
    @PostMapping(value = "externalcontact/get_moment_comments", headers = HEAD)
    CommentsResponse getMomentComments(CommentsRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 创建企业群发
     *
     * @param request 请求体
     * @param app     应用名
     * @return MsgTemplateResponse
     */
    @PostMapping(value = "externalcontact/add_msg_template", headers = HEAD)
    MsgTemplateResponse addMsgTemplate(MsgTemplateRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 获取群发记录列表
     *
     * @param request 请求体
     * @param app     应用名
     * @return GroupMsgResponse
     */
    @PostMapping(value = "externalcontact/get_groupmsg_list", headers = HEAD)
    GroupMsgResponse getGroupMsgList(GroupMsgRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 获取群发成员发送任务列表
     *
     * @param request 请求体
     * @param app     应用名
     * @return GroupMsgTaskResponse
     */
    @PostMapping(value = "externalcontact/get_groupmsg_task", headers = HEAD)
    GroupMsgTaskResponse getGroupMsgTask(GroupMsgTaskRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 获取企业群发成员执行结果
     *
     * @param request 请求体
     * @param app     应用名
     * @return GroupMsgSendResultResponse
     */
    @PostMapping(value = "externalcontact/get_groupmsg_send_result", headers = HEAD)
    GroupMsgSendResultResponse getGroupMsgSendResult(GroupMsgSendRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 发送新客户欢迎语
     *
     * @param request 请求体
     * @param app     应用名
     * @return BaseResponse
     */
    @PostMapping(value = "externalcontact/send_welcome_msg", headers = HEAD)
    BaseResponse sendWelcomeMsg(WelcomeMsgRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 添加入群欢迎语素材
     *
     * @param request 请求体
     * @param app     应用名
     * @return AddWelcomeTemplateResponse
     */
    @PostMapping(value = "externalcontact/group_welcome_template/add", headers = HEAD)
    AddWelcomeTemplateResponse addGroupWelcomeTemplate(WelcomeTemplateRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 编辑入群欢迎语素材
     *
     * @param request 请求体
     * @param app     应用名
     * @return BaseResponse
     */
    @PostMapping(value = "externalcontact/group_welcome_template/edit", headers = HEAD)
    BaseResponse editGroupWelcomeTemplate(WelcomeTemplateRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 获取入群欢迎语素材
     *
     * @param request 请求体
     * @param app     应用名
     * @return GetWelcomeTemplateResponse
     */
    @PostMapping(value = "externalcontact/group_welcome_template/get", headers = HEAD)
    GetWelcomeTemplateResponse getGroupWelcomeTemplate(GetWelcomeTemplateRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 删除入群欢迎语素材
     *
     * @param request 请求体
     * @param app     应用名
     * @return BaseResponse
     */
    @PostMapping(value = "externalcontact/group_welcome_template/del", headers = HEAD)
    BaseResponse deleteGroupWelcomeTemplate(DeleteWelcomeTemplateResponse request, @RequestParam(HEAD_KEY) String app);

    /**
     * 获取「联系客户统计」数据
     *
     * @param request 请求体
     * @param app     应用名
     * @return BehaviorDataResponse
     */
    @PostMapping(value = "externalcontact/get_user_behavior_data", headers = HEAD)
    BehaviorDataResponse getUserBehaviorData(UserBehaviorRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 按群主聚合的方式
     *
     * @param request 请求体
     * @param app     应用名
     * @return GroupChatStatisticResponse
     */
    @PostMapping(value = "externalcontact/groupchat/statistic", headers = HEAD)
    GroupChatStatisticResponse getGroupChatStatistic(GroupChatStatisticRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 按自然日聚合的方式
     *
     * @param request 请求体
     * @param app     应用名
     * @return GroupChatStatisticResponse
     */
    @PostMapping(value = "externalcontact/groupchat/statistic_group_by_day", headers = HEAD)
    GroupChatStatisticResponse getGroupChatStatisticByDay(GroupChatStatisticRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 创建日历
     *
     * @param request 请求体
     * @param app     应用名
     * @return CalendarResponse
     */
    @PostMapping(value = "oa/calendar/add", headers = HEAD)
    CalendarResponse addCalendar(CalendarRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 更新日历
     *
     * @param request 请求体
     * @param app     应用名
     * @return BaseResponse
     */
    @PostMapping(value = "oa/calendar/update", headers = HEAD)
    BaseResponse updateCalendar(CalendarRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 获取日历详情
     *
     * @param request 请求体
     * @param app     应用名
     * @return GetCalendarResponse
     */
    @PostMapping(value = "oa/calendar/get", headers = HEAD)
    GetCalendarResponse getCalendar(GetCalendarRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 删除日历
     *
     * @param request 请求体
     * @param app     应用名
     * @return BaseResponse
     */
    @PostMapping(value = "oa/calendar/del", headers = HEAD)
    BaseResponse delCalendar(DeleteCalendarRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 创建日程
     *
     * @param request 请求体
     * @param app     应用名
     * @return AddScheduleResponse
     */
    @PostMapping(value = "oa/schedule/add", headers = HEAD)
    AddScheduleResponse addSchedule(ScheduleRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 更新日程
     *
     * @param request 请求体
     * @param app     应用名
     * @return BaseResponse
     */
    @PostMapping(value = "oa/schedule/update", headers = HEAD)
    BaseResponse updateSchedule(ScheduleRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 获取日程详情
     *
     * @param request 请求体
     * @param app     应用名
     * @return GetScheduleResponse
     */
    @PostMapping(value = "oa/schedule/get", headers = HEAD)
    GetScheduleResponse getSchedules(GetScheduleRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 取消日程
     *
     * @param request 请求体
     * @param app     应用名
     * @return BaseResponse
     */
    @PostMapping(value = "oa/schedule/del", headers = HEAD)
    BaseResponse delSchedule(CancelScheduleRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 获取日历下的日程列表
     *
     * @param request 请求体
     * @param app     应用名
     * @return GetScheduleResponse
     */
    @PostMapping(value = "oa/schedule/get_by_calendar", headers = HEAD)
    GetScheduleResponse getScheduleByCalendar(GetScheduleByCalendarRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 创建预约会议
     *
     * @param request 请求体
     * @param app     应用名
     * @return CreateMeetingResponse
     */
    @PostMapping(value = "meeting/create", headers = HEAD)
    CreateMeetingResponse createMeeting(MeetingRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 修改预约会议
     *
     * @param request 请求体
     * @param app     应用名
     * @return BaseResponse updateMeeting(MeetingRequest request, @RequestParam(HEAD_KEY) String app);
     */
    @PostMapping(value = "meeting/update", headers = HEAD)
    BaseResponse updateMeeting(MeetingRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 取消预约会议
     *
     * @param request 请求体
     * @param app     应用名
     * @return BaseResponse
     */
    @PostMapping(value = "meeting/cancel", headers = HEAD)
    BaseResponse cancelMeeting(CancelMeetingRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 获取成员会议ID列表
     *
     * @param request 请求体
     * @param app     应用名
     * @return GetMeetingForUserResponse
     */
    @PostMapping(value = "meeting/get_user_meetingid", headers = HEAD)
    GetMeetingForUserResponse getUserMeetingId(GetMeetingForUserRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 获取会议详情
     *
     * @param request 请求体
     * @param app     应用名
     * @return MeetingDetailResponse
     */
    @PostMapping(value = "meeting/get_info", headers = HEAD)
    MeetingDetailResponse getMeetingInfo(GetMeetingDetailRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 创建预约直播
     *
     * @param request 请求体
     * @param app     应用名
     * @return CreateLivingResponse
     */
    @PostMapping(value = "living/create", headers = HEAD)
    CreateLivingResponse createLiving(LivingRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 修改预约直播
     *
     * @param request 请求体
     * @param app     应用名
     * @return BaseResponse
     */
    @PostMapping(value = "living/modify", headers = HEAD)
    BaseResponse modifyLiving(LivingRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 取消预约直播
     *
     * @param request 请求体
     * @param app     应用名
     * @return BaseResponse
     */
    @PostMapping(value = "living/cancel", headers = HEAD)
    BaseResponse cancelLiving(CancelLivingRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 删除直播回放
     *
     * @param request 请求体
     * @param app     应用名
     * @return BaseResponse
     */
    @PostMapping(value = "living/delete_replay_data", headers = HEAD)
    BaseResponse delLiving(DeleteLivingRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 获取微信观看直播凭证
     *
     * @param request 请求体
     * @param app     应用名
     * @return LivingCodeResponse
     */
    @PostMapping(value = "living/get_living_code", headers = HEAD)
    LivingCodeResponse getLivingCode(LivingCodeRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 获取成员直播ID列表
     *
     * @param request 请求体
     * @param app     应用名
     * @return GetUserLivingResponse
     */
    @PostMapping(value = "living/get_user_all_livingid", headers = HEAD)
    GetUserLivingResponse getUserAllLivingId(GetUserLivingRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 获取直播详情
     *
     * @param livingId 直播编号
     * @param app      应用名
     * @return LivingInfoResponse
     */
    @GetMapping(value = "living/get_living_info", headers = HEAD)
    LivingInfoResponse getLivingInfo(@RequestParam("livingid") String livingId, @RequestParam(HEAD_KEY) String app);

    /**
     * 获取直播观看明细
     *
     * @param request 请求体
     * @param app     应用名
     * @return WatchStatInfoResponse
     */
    @PostMapping(value = "living/get_watch_stat", headers = HEAD)
    WatchStatInfoResponse getWatchStat(GetWatchStatRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 获取跳转小程序商城的直播观众信息
     *
     * @param request 请求体
     * @param app     应用名
     * @return LivingShareResponse
     */
    @PostMapping(value = "living/get_living_share_info", headers = HEAD)
    LivingShareResponse getLivingShareInfo(LivingShareRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 获取企业所有打卡规则
     *
     * @param app 应用名
     * @return CheckInOptionResponse
     */
    @PostMapping(value = "checkin/getcorpcheckinoption", headers = HEAD)
    CheckInOptionResponse getCorpCheckInOption(@RequestParam(HEAD_KEY) String app);

    /**
     * 获取打卡日报数据
     *
     * @param request 请求体
     * @param app     应用名
     * @return CheckInDayReportResponse
     */
    @PostMapping(value = "checkin/getcheckin_daydata", headers = HEAD)
    CheckInDayReportResponse getCheckInDayData(CommonOaRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 获取打卡月报数据
     *
     * @param request 请求体
     * @param app     应用名
     * @return CheckInDayReportResponse
     */
    @PostMapping(value = "checkin/getcheckin_monthdata", headers = HEAD)
    CheckInDayReportResponse getCheckInMonthData(CommonOaRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 获取打卡人员排班信息
     *
     * @param request 请求体
     * @param app     应用名
     * @return CheckInScheduleResponse
     */
    @PostMapping(value = "checkin/getcheckinschedulist", headers = HEAD)
    CheckInScheduleResponse getCheckInScheduList(CommonOaRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 为打卡人员排班
     *
     * @param request 请求体
     * @param app     应用名
     * @return BaseResponse
     */
    @PostMapping(value = "checkin/setcheckinschedulist", headers = HEAD)
    BaseResponse setCheckInScheduleList(SetCheckInScheduleRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 录入打卡人员人脸信息
     *
     * @param request 请求体
     * @param app     应用名
     * @return BaseResponse
     */
    @PostMapping(value = "checkin/addcheckinuserface", headers = HEAD)
    BaseResponse addCheckInUserFace(AddCheckInUserFaceRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 获取企业微信API域名IP段
     *
     * @param app 应用名
     * @return ApiDomainIpResponse
     */
    @GetMapping(value = "get_api_domain_ip", headers = HEAD)
    ApiDomainIpResponse apiDomainIp(@RequestParam(HEAD_KEY) String app);

    /**
     * 获取企业微信服务器回调的ip段
     *
     * @param app 应用名
     * @return ApiDomainIpResponse
     */
    @GetMapping(value = "getcallbackip", headers = HEAD)
    ApiDomainIpResponse callBackIp(@RequestParam(HEAD_KEY) String app);

    /**
     * 获取审批模板详情
     *
     * @param request 请求体
     * @param app     应用名
     * @return ApproveTemplateResponse
     */
    @PostMapping(value = "oa/gettemplatedetail", headers = HEAD)
    ApproveTemplateResponse getTemplateDetail(TemplateRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 提交审批申请
     *
     * @param request 请求体
     * @param app     应用名
     * @return ApplyEventResponse
     */
    @PostMapping(value = "oa/applyevent", headers = HEAD)
    ApplyEventResponse applyEvent(ApplyEventRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 批量获取审批单号
     *
     * @param request 请求体
     * @param app     应用名
     * @return SpNoResponse
     */
    @PostMapping(value = "oa/getapprovalinfo", headers = HEAD)
    SpNoResponse getApprovalInfo(GetApprovalNoRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 获取审批申请详情
     *
     * @param request 请求体
     * @param app     应用名
     * @return ApprovalDetailResponse
     */
    @PostMapping(value = "oa/getapprovaldetail", headers = HEAD)
    ApprovalDetailResponse getApprovalDetail(ApprovalDetailRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 获取企业假期管理配置
     *
     * @param app 应用名
     * @return VacationConfigResponse
     */
    @GetMapping(value = "oa/vacation/getcorpconf", headers = HEAD)
    VacationConfigResponse getCorpConf(@RequestParam(HEAD_KEY) String app);

    /**
     * 获取成员假期余额
     *
     * @param request 请求体
     * @param app     应用名
     * @return UserVacationQuotaResponse
     */
    @PostMapping(value = "oa/vacation/getuservacationquota", headers = HEAD)
    UserVacationQuotaResponse getUserVacationQuota(UserIdRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 修改成员假期余额
     *
     * @param request 请求体
     * @param app     应用名
     * @return BaseResponse
     */
    @PostMapping(value = "oa/vacation/setoneuserquota", headers = HEAD)
    BaseResponse setOneUserQuota(UpdateUserQuotaRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 批量获取汇报记录单号
     *
     * @param request 请求体
     * @param app     应用名
     * @return JournalRecordResponse
     */
    @PostMapping(value = "oa/journal/get_record_list", headers = HEAD)
    JournalRecordResponse getJournalRecordList(JournalRecordRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 获取汇报记录详情
     *
     * @param request 请求体
     * @param app     应用名
     * @return JournalRecordResponse
     */
    @PostMapping(value = "oa/journal/get_record_detail", headers = HEAD)
    JournalReportDetailResponse getJournalRecordDetail(JournalReportDetailRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 获取汇报统计数据
     *
     * @param request 请求体
     * @param app     应用名
     * @return JournalReportStatResponse
     */
    @PostMapping(value = "oa/journal/get_stat_list", headers = HEAD)
    JournalReportStatResponse getJournalStatList(JournalReportStatRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 添加会议室
     *
     * @param request 请求体
     * @param app     应用名
     * @return JournalReportStatResponse
     */
    @PostMapping(value = "oa/meetingroom/add", headers = HEAD)
    AddMeetingRoomResponse addMeetingRoom(MeetingRoomRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 查询会议室
     *
     * @param request 请求体
     * @param app     应用名
     * @return MeetingRoomListResponse
     */
    @PostMapping(value = "oa/meetingroom/list", headers = HEAD)
    MeetingRoomListResponse searchMeetingRoom(MeetingRoomRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 编辑会议室
     *
     * @param request 请求体
     * @param app     应用名
     * @return MeetingRoomListResponse
     */
    @PostMapping(value = "oa/meetingroom/edit", headers = HEAD)
    BaseResponse updateMeetingRoom(MeetingRoomRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 删除会议室
     *
     * @param request 请求体
     * @param app     应用名
     * @return MeetingRoomListResponse
     */
    @PostMapping(value = "oa/meetingroom/del", headers = HEAD)
    BaseResponse delMeetingRoom(MeetingRoomRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 查询会议室的预定信息
     *
     * @param request 请求体
     * @param app     应用名
     * @return BookingInfoResponse
     */
    @PostMapping(value = "oa/meetingroom/get_booking_info", headers = HEAD)
    BookingInfoResponse getBookingInfo(GetBookingInfoRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 预定会议室
     *
     * @param request 请求体
     * @param app     应用名
     * @return BookMeetingRoomResponse
     */
    @PostMapping(value = "oa/meetingroom/book", headers = HEAD)
    BookMeetingRoomResponse bookMeetingRoom(BookMeetingRoomRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 取消预定会议室
     *
     * @param request 请求体
     * @param app     应用名
     * @return BookMeetingRoomResponse
     */
    @PostMapping(value = "oa/meetingroom/cancel_book", headers = HEAD)
    BaseResponse cancelBookMeetingRoom(CancelBookRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 发起语音电话
     *
     * @param request 请求体
     * @param app     应用名
     * @return EmergencyCallResponse
     */
    @PostMapping(value = "pstncc/call", headers = HEAD)
    EmergencyCallResponse pstnccCall(EmergencyCallRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 获取接听状态
     *
     * @param request 请求体
     * @param app     应用名
     * @return EmergencyCallResponse
     */
    @PostMapping(value = "pstncc/getstates", headers = HEAD)
    GetCallStateResponse pstnccCallState(GetCallStateRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 获取加入企业二维码
     *
     * @param sizeType qrcode尺寸类型
     * @param app      应用名
     * @return 二维码地址
     */
    @GetMapping(value = "corp/get_join_qrcode", headers = HEAD)
    JoinQrCodeResponse getJoinQrCode(@RequestParam("size_type") Integer sizeType, @RequestParam(HEAD_KEY) String app);

    /**
     * 获取企业活跃成员数
     *
     * @param request 请求体
     * @param app     应用名
     * @return 企业活跃成员数
     */
    @PostMapping(value = "user/get_active_stat", headers = HEAD)
    ActiveStatResponse getActiveStat(ActiveStatRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 导出成员
     *
     * @param request 请求体
     * @param app     应用名
     * @return 异步任务编号
     */
    @PostMapping(value = "export/simple_user", headers = HEAD)
    AsyncJobResponse exportUser(AddressBookExportRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 获取导出结果
     *
     * @param jobId 任务编号
     * @param app   应用名
     * @return 结果
     */
    @GetMapping(value = "export/get_result", headers = HEAD)
    ExportResultResponse getExportResult(@RequestParam("jobid") String jobId, @RequestParam(HEAD_KEY) String app);

    /**
     * 导出成员详情
     *
     * @param request 请求体
     * @param app     应用名
     * @return 异步任务编号
     */
    @PostMapping(value = "export/user", headers = HEAD)
    AsyncJobResponse exportUserDetail(AddressBookExportRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 导出部门
     *
     * @param request 请求体
     * @param app     应用名
     * @return 异步任务编号
     */
    @PostMapping(value = "export/department", headers = HEAD)
    AsyncJobResponse exportDepartment(AddressBookExportRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 导出标签成员
     *
     * @param request 请求体
     * @param app     应用名
     * @return 异步任务编号
     */
    @PostMapping(value = "export/taguser", headers = HEAD)
    AsyncJobResponse exportTagUser(AddressBookExportRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 添加客服帐号
     *
     * @param request 请求
     * @param app     应用
     * @return 新创建的客服帐号ID
     */
    @PostMapping(value = "kf/account/add", headers = HEAD)
    AddKfAccountResponse addKfAccount(KfAccountRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 删除客服帐号
     *
     * @param request 请求
     * @param app     应用
     * @return 无
     */
    @PostMapping(value = "kf/account/del", headers = HEAD)
    BaseResponse delKfAccount(DelKfAccountRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 修改客服帐号
     *
     * @param request 请求
     * @param app     应用
     * @return 无
     */
    @PostMapping(value = "kf/account/update", headers = HEAD)
    BaseResponse updateKfAccount(KfAccountRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 获取客服帐号列表
     *
     * @param app 应用名称
     * @return 账户列表
     */
    @GetMapping(value = "kf/account/list", headers = HEAD)
    KfAccountListResponse kfAccountLIst(@RequestParam(HEAD_KEY) String app);

    /**
     * 获取客服帐号链接
     *
     * @param request 请求
     * @param app     应用名称
     * @return 客服帐号链接
     */
    @PostMapping(value = "kf/add_contact_way", headers = HEAD)
    KfAddContactWayResponse kfContactWay(KfAddContactWayRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 添加接待人员
     *
     * @param request 请求
     * @param app     应用
     * @return 添加结果
     */
    @PostMapping(value = "kf/servicer/add", headers = HEAD)
    ServicerResponse addServicer(ServicerRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 删除接待人员
     *
     * @param request 请求
     * @param app     应用
     * @return 删除结果
     */
    @PostMapping(value = "kf/servicer/del", headers = HEAD)
    ServicerResponse delServicer(ServicerRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 获取接待人员列表
     *
     * @param openKfId 客服编号
     * @param app      应用
     * @return 接待人员列表
     */
    @GetMapping(value = "kf/servicer/list", headers = HEAD)
    ServicerListResponse getServicerList(@RequestParam("open_kfid") String openKfId, @RequestParam(HEAD_KEY) String app);

    /**
     * 获取会话状态
     *
     * @param request 请求
     * @param app     应用
     * @return 会话状态
     */
    @PostMapping(value = "kf/service_state/get", headers = HEAD)
    ServiceStateResponse getServiceState(ServiceStateRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 变更会话状态
     *
     * @param request 请求
     * @param app     应用
     * @return msgCode
     */
    @PostMapping(value = "kf/service_state/trans", headers = HEAD)
    ChangeServiceStateResponse changeServiceState(ChangeServiceStateRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 读取消息
     *
     * @param request 请求
     * @param app     应用
     * @return 消息
     */
    @PostMapping(value = "kf/sync_msg", headers = HEAD)
    SyncMsgResponse syncMsg(SyncMsgRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 发送消息
     *
     * @param msg 消息
     * @param app 应用
     * @return msgID
     */
    @PostMapping(value = "kf/send_msg", headers = HEAD)
    SendMsgResponse sendMsg(SendMsgRequest msg, @RequestParam(HEAD_KEY) String app);

    /**
     * 发送事件响应消息
     *
     * @param request 消息
     * @param app     应用
     * @return msgID
     */
    @PostMapping(value = "kf/send_msg_on_event", headers = HEAD)
    SendMsgResponse sendMsgOnEvent(SendMsgOnEventRequest request, @RequestParam(HEAD_KEY) String app);

    /**
     * 获取客户基础信息
     *
     * @param request 请求
     * @param app     应用
     * @return 客户信息
     */
    @PostMapping(value = "kf/customer/batchget", headers = HEAD)
    GetCustomerResponse getCustomer(GetCustomerRequest request, @RequestParam(HEAD_KEY) String app);
}
