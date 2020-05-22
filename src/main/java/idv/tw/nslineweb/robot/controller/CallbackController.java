package idv.tw.nslineweb.robot.controller;

import static java.util.Collections.singletonList;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.action.DatetimePickerAction;
import com.linecorp.bot.model.action.MessageAction;
import com.linecorp.bot.model.action.PostbackAction;
import com.linecorp.bot.model.action.URIAction;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.StickerMessageContent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.event.source.GroupSource;
import com.linecorp.bot.model.message.ImageMessage;
import com.linecorp.bot.model.message.ImagemapMessage;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.StickerMessage;
import com.linecorp.bot.model.message.TemplateMessage;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.message.imagemap.ImagemapArea;
import com.linecorp.bot.model.message.imagemap.ImagemapBaseSize;
import com.linecorp.bot.model.message.imagemap.MessageImagemapAction;
import com.linecorp.bot.model.message.imagemap.URIImagemapAction;
import com.linecorp.bot.model.message.template.ButtonsTemplate;
import com.linecorp.bot.model.message.template.CarouselColumn;
import com.linecorp.bot.model.message.template.CarouselTemplate;
import com.linecorp.bot.model.message.template.ConfirmTemplate;
import com.linecorp.bot.model.message.template.ImageCarouselColumn;
import com.linecorp.bot.model.message.template.ImageCarouselTemplate;
import com.linecorp.bot.model.response.BotApiResponse;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;

@LineMessageHandler
public class CallbackController {

	private static final Logger log = LoggerFactory.getLogger(CallbackController.class);

    @Value("${line.bot.channelSecret}")
    private String channelSecret;
    
    @Value("${line.bot.channelToken}")
    private String channelToken;
	
	@Autowired
	private LineMessagingClient lineMessagingClient;

	@EventMapping
	public void handleTextMessageEvent(MessageEvent<TextMessageContent> event) throws Exception {
		log.info("TextMessage event: " + event);
		handleTextContent(event.getReplyToken(), event, event.getMessage());
	}

	@EventMapping
	public void handleStickerMessageEvent(MessageEvent<StickerMessageContent> event) {
		log.info("StickerMessage event: " + event);
		reply(event.getReplyToken(), new StickerMessage(event.getMessage().getPackageId(), event.getMessage().getStickerId()));
	}

	@EventMapping
	public void handleDefaultMessageEvent(Event event) {
		System.out.println("handleDefaultMessageEvent event: " + event);
	}

	/**
	 * 
	 * @param text
	 * @return
	 */
	private static String extractCommand(String text)
	{
		Pattern p = Pattern.compile(".*?(\\[.*?[^\\[]\\]).*?");
		Matcher m = p.matcher(text);
		while(m.find())
		{
			System.out.println( m.group(1) );
			return m.group(1);
		}
		return text;
	}
	
    private void handleTextContent(String replyToken, Event event, TextMessageContent content)
            throws Exception {
        final String text = content.getText();

        log.info("Got text message from replyToken:{}: text:{}", replyToken, text);
        switch (extractCommand(text).toLowerCase()) {
        	case "[移轉單]":{
        		lineMessagingClient.getProfile(event.getSource().getUserId()).whenComplete( (profile,throwable) -> {
                    ConfirmTemplate confirmTemplate = new ConfirmTemplate(
                            String.format("HI %s, 請問是否填寫移轉單內容?", profile.getDisplayName()),
                            new MessageAction("Yes", "Yes!"),
                            new MessageAction("No", "No!")
                    );
                    TemplateMessage templateMessage = new TemplateMessage("Confirm alt text", confirmTemplate);
                    this.reply(replyToken, templateMessage);
        		});
        		break;
        	}
	        case "?": {
	        	StringBuffer sb = new StringBuffer();
	        	sb.append("測試命令以下:").append("\r\n");
	        	sb.append("1. profile -> 回傳用戶資訊").append("\r\n");
	        	sb.append("2. confirm -> 回傳Confirm視窗").append("\r\n");
	        	sb.append("3. buttons -> description").append("\r\n");
	        	sb.append("4. carousel -> description").append("\r\n");
	        	sb.append("5. image_carousel -> description").append("\r\n");
	        	sb.append("6. imagemap -> description").append("\r\n");
	        	sb.append("7. 其它字元 -> 回傳相同文字").append("\r\n");
	        	this.reply(replyToken, new TextMessage(sb.toString()));
	        	break;
	        }
            case "profile": {
                log.info("Invoking 'profile' command: source:{}",
                         event.getSource());
                final String userId = event.getSource().getUserId();
                if (userId != null) {
                    if (event.getSource() instanceof GroupSource) {
                        lineMessagingClient
                                .getGroupMemberProfile(((GroupSource) event.getSource()).getGroupId(), userId)
                                .whenComplete((profile, throwable) -> {
                                    if (throwable != null) {
                                        this.replyText(replyToken, throwable.getMessage());
                                        return;
                                    }

                                    this.reply(
                                            replyToken,
                                            Arrays.asList(new TextMessage("(from group)"),
                                                          new TextMessage("Display name: " + profile.getDisplayName() + "("+profile.getUserId()+")"),
                                                          new ImageMessage(profile.getPictureUrl(), profile.getPictureUrl()))
                                    );
                                });
                    } else {
                        lineMessagingClient
                                .getProfile(userId)
                                .whenComplete((profile, throwable) -> {
                                    if (throwable != null) {
                                        this.replyText(replyToken, throwable.getMessage());
                                        return;
                                    }

                                    this.reply(
                                       replyToken,
                                            Arrays.asList(new TextMessage("Display name: " + profile.getDisplayName() + "("+profile.getUserId()+")"),
                                                          new ImageMessage(profile.getPictureUrl(), profile.getPictureUrl()))
                                    );
                                });
                    }
                } else {
                    this.replyText(replyToken, "Bot can't use profile API without user ID");
                }
                break;
            }
            case "bye": {
//                Source source = event.getSource();
//                if (source instanceof GroupSource) {
//                    this.replyText(replyToken, "Leaving group");
//                    lineMessagingClient.leaveGroup(((GroupSource) source).getGroupId()).get();
//                } else if (source instanceof RoomSource) {
//                    this.replyText(replyToken, "Leaving room");
//                    lineMessagingClient.leaveRoom(((RoomSource) source).getRoomId()).get();
//                } else {
//                    this.replyText(replyToken, "Bot can't leave from 1:1 chat");
//                }
                break;
            }
            case "confirm": {
                ConfirmTemplate confirmTemplate = new ConfirmTemplate(
                        "Do it?",
                        new MessageAction("Yes", "Yes!"),
                        new MessageAction("No", "No!")
                );
                TemplateMessage templateMessage = new TemplateMessage("Confirm alt text", confirmTemplate);
                this.reply(replyToken, templateMessage);
                break;
            }
            case "buttons": {
				ButtonsTemplate buttonsTemplate = new ButtonsTemplate("https://ns-linebot.herokuapp.com/buttons/1040.jpg", "My button sample", "Hello, my button",
						Arrays.asList(new URIAction("Go to line.me", "https://line.me", null),
								new PostbackAction("Say hello1", "hello こんにちは"),
								new PostbackAction("言 hello2", "hello こんにちは", "hello こんにちは"),
								new MessageAction("Say message", "Rice=米")));
                TemplateMessage templateMessage = new TemplateMessage("Button alt text", buttonsTemplate);
                this.reply(replyToken, templateMessage);
                break;
            }
            case "carousel": {
            	String imageUrl = "https://ns-linebot.herokuapp.com/buttons/1040.jpg";
                CarouselTemplate carouselTemplate = new CarouselTemplate(
                        Arrays.asList(
                                new CarouselColumn(imageUrl, "hoge", "fuga", Arrays.asList(
                                        new URIAction("Go to line.me",
                                                      "https://line.me", null),
                                        new URIAction("Go to line.me",
                                                      "https://line.me", null),
                                        new PostbackAction("Say hello1",
                                                           "hello こんにちは")
                                )),
                                new CarouselColumn(imageUrl, "hoge", "fuga", Arrays.asList(
                                        new PostbackAction("言 hello2",
                                                           "hello こんにちは",
                                                           "hello こんにちは"),
                                        new PostbackAction("言 hello2",
                                                           "hello こんにちは",
                                                           "hello こんにちは"),
                                        new MessageAction("Say message",
                                                          "Rice=米")
                                )),
                                new CarouselColumn(imageUrl, "Datetime Picker",
                                                   "Please select a date, time or datetime", Arrays.asList(
                                        new DatetimePickerAction("Datetime", "action=sel", "time", "2017-06-18T06:15", "1900-01-01T00:00", "2100-12-31T23:59"), 
                                        new DatetimePickerAction("Date", "action=sel&only=date", "date", "2017-06-18", "1900-01-01", "2100-12-31"), 
                                        new DatetimePickerAction("Time", "action=sel&only=time", "time", "06:15", "00:00", "23:59")
                                ))
                        ));
                TemplateMessage templateMessage = new TemplateMessage("Carousel alt text", carouselTemplate);
                this.reply(replyToken, templateMessage);
                break;
            }
            case "image_carousel": {
                String imageUrl = "https://ns-linebot.herokuapp.com/buttons/1040.jpg";
                ImageCarouselTemplate imageCarouselTemplate = new ImageCarouselTemplate(
                    Arrays.asList(
						new ImageCarouselColumn(imageUrl, new URIAction("Goto line.me", "https://line.me", null)),
						new ImageCarouselColumn(imageUrl, new MessageAction("Say message", "Rice=米")),
						new ImageCarouselColumn(imageUrl, new PostbackAction("言 hello2", "hello こんにちは", "hello こんにちは"))
                    ));
                TemplateMessage templateMessage = new TemplateMessage("ImageCarousel alt text", imageCarouselTemplate);
                this.reply(replyToken, templateMessage);
                break;
            }
            case "imagemap":
                this.reply(replyToken, ImagemapMessage
                        .builder()
                        .baseUrl("https://ns-linebot.herokuapp.com/rich")
                        .altText("This is alt text")
                        .baseSize(new ImagemapBaseSize(1040, 1040))
                        .actions(Arrays.asList(
                        		new URIImagemapAction("https://store.line.me/family/manga/en", new ImagemapArea(0, 0, 520, 520)),
                        		new URIImagemapAction("https://store.line.me/family/music/en", new ImagemapArea(520, 0, 520, 520)),
                                new URIImagemapAction("https://store.line.me/family/play/en", new ImagemapArea(0, 520, 520, 520)),
                                new MessageImagemapAction("URANAI!", new ImagemapArea(520, 520, 520, 520))
                        ))
                        .build());
                break;
            case "imagemap_video":
//                this.reply(replyToken, ImagemapMessage
//                        .builder()
//                        .baseUrl("/static/imagemap_video")
//                        .altText("This is an imagemap with video")
//                        .baseSize(new ImagemapBaseSize(722, 1040))
//                        .video(
//                                ImagemapVideo.builder()
//                                             .originalContentUrl(
//                                                     createUri("/static/imagemap_video/originalContent.mp4"))
//                                             .previewImageUrl(
//                                                     createUri("/static/imagemap_video/previewImage.jpg"))
//                                             .area(new ImagemapArea(40, 46, 952, 536))
//                                             .externalLink(
//                                                     new ImagemapExternalLink(
//                                                             URI.create("https://example.com/see_more.html"),
//                                                             "See More")
//                                             )
//                                             .build()
//                        )
//                        .actions(singletonList(
//                                MessageImagemapAction.builder()
//                                                     .text("NIXIE CLOCK")
//                                                     .area(new ImagemapArea(260, 600, 450, 86))
//                                                     .build()
//                        ))
//                        .build());
                break;
            case "flex":
                //this.reply(replyToken, new ExampleFlexMessageSupplier().get());
                break;
            case "quickreply":
                //this.reply(replyToken, new MessageWithQuickReplySupplier().get());
                break;
            case "no_notify":
                this.reply(replyToken,
                           singletonList(new TextMessage("This message is send without a push notification")),
                           true);
                break;
            default:
                log.info("Returns echo message {}: {}", replyToken, text);
                this.replyText(
                        replyToken,
                        text
                );
                break;
        }
    }

	private void replyText(String replyToken, String message) {
		if (replyToken.isEmpty()) {
			throw new IllegalArgumentException("replyToken must not be empty");
		}
		if (message.length() > 1000) {
			message = message.substring(0, 1000 - 3) + "...";
		}
		this.reply(replyToken, new TextMessage(message));
	}

	private void reply(String replyToken, Message message) {
		reply(replyToken, singletonList(message));
	}

	private void reply(String replyToken, List<Message> messages) {
		reply(replyToken, messages, false);
	}

	private void reply(String replyToken, List<Message> messages, boolean notificationDisabled) {
		try {
			BotApiResponse apiResponse = lineMessagingClient.replyMessage(new ReplyMessage(replyToken, messages)).get();
			log.info("Sent messages: {}", apiResponse);
		} catch (InterruptedException | ExecutionException e) {
			throw new RuntimeException(e);
		}
	}

	private static URI createUri(String path) {
		return ServletUriComponentsBuilder.fromCurrentContextPath().scheme("https").path(path).build().toUri();
	}
}
