//package com.example.javatest.socket;
//
//
//import kr.co.delphinet.dto.bizApi.request.*;
//import kr.co.delphinet.dto.bizApi.response.*;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.codehaus.jackson.JsonParser.Feature;
//import org.codehaus.jackson.map.ObjectMapper;
//import org.codehaus.jackson.type.JavaType;
//
//
//public class BizApiSender {
//    private static final Log log = LogFactory.getLog(BizApiSender.class);
//    private static final ObjectMapper mapper;
//    private static final String REGIST_TEMPLATE_URL = "/v3/kakao/template/add";
//    private static final String DETAIL_TEMPLATE_URL = "/v3/kakao/template/detail";
//    private static final String MODIFY_TEMPLATE_URL = "/v3/kakao/template/update";
//    private static final String DELETE_TEMPLATE_URL = "/v3/kakao/template/delete";
//    private static final String REGIST_REPLY_URL = "/v3/kakao/template/comment";
//    private static final String REQUEST_APPROVAL_URL = "/v3/kakao/template/request";
//
//    public BizApiSender() {
//    }
//
//    public static ResResponseBaseModel<ResRegistTemplateModel> registTemplate(String prefixUrl, ReqRegistTemplateModel model) throws Exception {
//        return (ResResponseBaseModel<ResRegistTemplateModel>) getResponse(prefixUrl + REGIST_TEMPLATE_URL, model, ResRegistTemplateModel.class);
//    }
//
//    public static ResResponseBaseModel<ResDetailTemplateModel> detailTemplate(String prefixUrl, ReqDetailTemplateModel model) throws Exception {
//        return (ResResponseBaseModel<ResDetailTemplateModel>) getResponse(prefixUrl + DETAIL_TEMPLATE_URL, model, ResDetailTemplateModel.class);
//    }
//
//    public static ResResponseBaseModel<ResModifyTemplateModel> modifyTemplate(String prefixUrl, ReqModifyTemplateModel model) throws Exception {
//        return (ResResponseBaseModel<ResModifyTemplateModel>) getResponse(prefixUrl + MODIFY_TEMPLATE_URL, model, ResModifyTemplateModel.class);
//    }
//
//    public static ResResponseBaseModel<ResDeleteTemplateModel> deleteTemplate(String prefixUrl, ReqDeleteTemplateModel model) throws Exception {
//        return (ResResponseBaseModel<ResDeleteTemplateModel>) getResponse(prefixUrl + DELETE_TEMPLATE_URL, model, ResDeleteTemplateModel.class);
//    }
//
//    public static ResResponseBaseModel<ResRegistReplyCommentModel> registComment(String prefixUrl, ReqRegistCommentModel model) throws Exception {
//        return (ResResponseBaseModel<ResRegistReplyCommentModel>) getResponse(prefixUrl + REGIST_REPLY_URL, model, ResRegistReplyCommentModel.class);
//    }
//
//    public static ResResponseBaseModel<ResRequestApprovalModel> requestApproval(String prefixUrl, ReqRequestApprovalModel model) throws Exception {
//        return (ResResponseBaseModel<ResRequestApprovalModel>) getResponse(prefixUrl + REQUEST_APPROVAL_URL, model, ResRequestApprovalModel.class);
//    }
//
//    private static ResResponseBaseModel<?> getResponse(String url, Object model, Class<?> parameterClazz) throws Exception {
//        String response = HTTPClientUtlis.sendPost(url, mapper.writeValueAsString(model));
//        if (StringUtils.isBlank(response)) {
//            log.warn("Response is empty...");
//        }
//
//        JavaType type = mapper.getTypeFactory().constructParametricType(ResResponseBaseModel.class, new Class[]{parameterClazz});
//        ResResponseBaseModel<?> responseModel = mapper.readValue(response, type);
//        if (!"200".equals(responseModel.getCode())) {
//            throw new IllegalStateException(responseModel.getMessage());
//        } else {
//            return mapper.readValue(response, type);
//        }
//    }
//
//    static {
//        mapper = (new ObjectMapper()).configure(Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
//    }
//}
