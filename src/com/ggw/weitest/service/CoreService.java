package com.ggw.weitest.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.ggw.weitest.message.resp.Article;
import com.ggw.weitest.message.resp.NewsMessage;
import com.ggw.weitest.message.resp.TextMessage;
import com.ggw.weitest.util.MessageUtil;

public class CoreService {

	 /** 
     * 处理微信发来的请求 
     *  
     * @param request 
     * @return 
     */  
    public static String processRequest(HttpServletRequest request) {  
        String respMessage = null;  
        try {  
            // 默认返回的文本消息内容  
            String respContent = "请求处理异常，请稍候尝试！";  
  
            // xml请求解析  
            Map<String, String> requestMap = MessageUtil.parseXml(request);  
  
            // 发送方帐号（open_id）  
            String fromUserName = requestMap.get("FromUserName");  
            // 公众帐号  
            String toUserName = requestMap.get("ToUserName");  
            // 消息类型  
            String msgType = requestMap.get("MsgType");  
  
  
            // 文本消息  
            if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {  
            	// 回复文本消息  
                TextMessage textMessage = new TextMessage();  
                textMessage.setToUserName(fromUserName);  
                textMessage.setFromUserName(toUserName);  
                textMessage.setCreateTime(new Date().getTime());  
                textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);  
                textMessage.setFuncFlag(0);
                respContent = "<a href=\"http://www.tyfda.gov.cn\">您发送的是文本消息！</a>"; 
                textMessage.setContent(respContent);  
                respMessage = MessageUtil.textMessageToXml(textMessage);
            }  
            // 图片消息  
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) { 
            	// 回复文本消息  
                TextMessage textMessage = new TextMessage();  
                textMessage.setToUserName(fromUserName);  
                textMessage.setFromUserName(toUserName);  
                textMessage.setCreateTime(new Date().getTime());  
                textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);  
                textMessage.setFuncFlag(0);
                respContent = "您发送的是图片消息！[呲牙]";  
                textMessage.setContent(respContent);  
                respMessage = MessageUtil.textMessageToXml(textMessage);
            }  
            // 地理位置消息  
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {  
                respContent = "您发送的是地理位置消息！";  
            }  
            // 链接消息  
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {  
                respContent = "您发送的是链接消息！";  
            }  
            // 音频消息  
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
            	NewsMessage newsMessage = new NewsMessage();
            	newsMessage.setToUserName(fromUserName);  
                newsMessage.setFromUserName(toUserName);  
                newsMessage.setCreateTime(new Date().getTime());  
                newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);  
                newsMessage.setFuncFlag(0);  
  
                List<Article> articleList = new ArrayList<Article>();
	        	Article article = new Article();  
	            article.setTitle("高光伟");  
	            article.setDescription("高光伟牛逼");  
	            article.setPicUrl("http://115.159.42.28/weitest/1.jpg");  
	            article.setUrl("http://www.baidu.com");  
	            articleList.add(article);  
	            // 设置图文消息个数  
	            newsMessage.setArticleCount(articleList.size());  
	            // 设置图文消息包含的图文集合  
	            newsMessage.setArticles(articleList);  
	            // 将图文消息对象转换成xml字符串  
	            respMessage = MessageUtil.newsMessageToXml(newsMessage);  
                //respContent = "您发送的是音频消息！";  
            }  
            // 事件推送  
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {  
                // 事件类型  
                String eventType = requestMap.get("Event");  
                // 订阅  
                if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {  
                    respContent = "谢谢您的关注！";  
                }  
                // 取消订阅  
                else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {  
                    // TODO 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息  
                }  
                // 自定义菜单点击事件  
                else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {  
                	String eventKey = requestMap.get("EventKey");  
                	
                	TextMessage textMessage = new TextMessage();  
                    textMessage.setToUserName(fromUserName);  
                    textMessage.setFromUserName(toUserName);  
                    textMessage.setCreateTime(new Date().getTime());  
                    textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);  
                    textMessage.setFuncFlag(0);
                	  
                    if (eventKey.equals("11")) {  
                        respContent = "天气预报菜单项被点击！";  
                    } else if (eventKey.equals("12")) {  
                        respContent = "公交查询菜单项被点击！";  
                    } else if (eventKey.equals("13")) {  
                        respContent = "周边搜索菜单项被点击！";  
                    } else if (eventKey.equals("14")) {  
                        respContent = "历史上的今天菜单项被点击！";  
                    } else if (eventKey.equals("21")) {  
                        respContent = "歌曲点播菜单项被点击！";  
                    } else if (eventKey.equals("22")) {  
                        respContent = "经典游戏菜单项被点击！";  
                    } else if (eventKey.equals("23")) {  
                        respContent = "美女电台菜单项被点击！";  
                    } else if (eventKey.equals("24")) {  
                        respContent = "人脸识别菜单项被点击！";  
                    } else if (eventKey.equals("25")) {  
                        respContent = "聊天唠嗑菜单项被点击！";  
                    } else if (eventKey.equals("31")) {  
                        respContent = "Q友圈菜单项被点击！";  
                    } else if (eventKey.equals("32")) {  
                        respContent = "电影排行榜菜单项被点击！";  
                    } else if (eventKey.equals("33")) {  
                        respContent = "幽默笑话菜单项被点击！";  
                    } 
                    textMessage.setContent(respContent);  
                    respMessage = MessageUtil.textMessageToXml(textMessage);
                }  
            }  
  
            //textMessage.setContent(respContent);  
            //respMessage = MessageUtil.textMessageToXml(textMessage);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
  
        return respMessage;  
    }  
}
