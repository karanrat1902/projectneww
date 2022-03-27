package com.karanrat.linebot;

import com.google.common.io.ByteStreams;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.client.MessageContentResponse;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.ImageMessageContent;
import com.linecorp.bot.model.event.message.LocationMessageContent;
import com.linecorp.bot.model.event.message.StickerMessageContent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.*;
import com.linecorp.bot.model.response.BotApiResponse;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;

import lombok.NonNull;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import java.util.Random;

@Slf4j
@LineMessageHandler

public class LineBotController extends Somtea implements randomfood {
    Random rand = new Random();
    int logi=0,logic = 0,logicc=0;
    int ticket=0 ;

    @Autowired
    private LineMessagingClient lineMessagingClient;


    @EventMapping
    public void handleTextMessage(MessageEvent<TextMessageContent> event) {
        log.info(event.toString());
        TextMessageContent message = event.getMessage();
        handleTextContent(event.getReplyToken(), event, message);
    }

    @EventMapping
    public void handleStickerMessage(MessageEvent<StickerMessageContent> event) {
        log.info(event.toString());
        StickerMessageContent message = event.getMessage();
        reply(event.getReplyToken(), new StickerMessage(
                message.getPackageId(), message.getStickerId()
        ));
    }

    @EventMapping
    public void handleLocationMessage(MessageEvent<LocationMessageContent> event) {
        log.info(event.toString());
        LocationMessageContent message = event.getMessage();
        reply(event.getReplyToken(), new LocationMessage(
                (message.getTitle() == null) ? "Location replied" : message.getTitle(),
                message.getAddress(),
                message.getLatitude(),
                message.getLongitude()
        ));
    }

    @EventMapping
    public void handleImageMessage(MessageEvent<ImageMessageContent> event) {
        log.info(event.toString());
        ImageMessageContent content = event.getMessage();
        String replyToken = event.getReplyToken();

        try {
            MessageContentResponse response = lineMessagingClient.getMessageContent(content.getId()).get();
            DownloadedContent jpg = saveContent("jpg", response);
            DownloadedContent previewImage = createTempFile("jpg");

            system("convert", "-resize", "240x",
                    jpg.path.toString(),
                    previewImage.path.toString());

            reply(replyToken, new ImageMessage(jpg.getUri(), previewImage.getUri()));

        } catch (InterruptedException | ExecutionException e) {
            reply(replyToken, new TextMessage("Cannot get image: " + content));
            throw new RuntimeException(e);
        }

    }

    public String checktext(String text) {
        String word = text;
        String messagech;
        if ((word.equals("กินไรดีง่ะ")) || (word.equals("หิว")) || (word.equals("กินไรดี")) || (word.equals("กินอะไรดีเนี่ย")) || (word.equals("หิวอ่ะ"))                                                                                                    || (word.equals(""))) {
            messagech = "ไม่รู้จะกินอะไรดี"; 
        } else if ((word.equals("ไม่"))){
            logi = 0 ;
            logic = 0 ;
            messagech = "ไม่";
        } else if ((word.equals("ใช่"))){
            logicc=0;
            logi = 1 ;
            messagech = "ใช่";
        }else {
            
            logicc=0;
            messagech = word;
            
        }

        return messagech;
    }
    


    public String getRandomFood(){
        int n = rand.nextInt(5);
        if(n==1){
            setFood("ไข่กะทะ(f1)");
        }else if(n==2){
            setFood("มินิพิซซ่าแฮมชีส(f2)");
        }else if(n==3){
            setFood("แซนด์วิชไก่กรอบ(f3)");
        }else if(n==4){
            setFood("สลัดไข่เจียว(f4)");
        }else if(n==5){
            setFood("สเต๊กหมูพันเบคอน(f5)");
        }
        String food = getFood();
        return food;
    }

    public String getRandomCoffee(){
        int n = rand.nextInt(5);
        if(n==1){
            setCoffee("เอสเพรสโซ(c1)");
        }else if(n==2){
            setCoffee("อเมริกาโน(c2)");
        }else if(n==3){
            setCoffee("ลาเต้(c3)");
        }else if(n==4){
            setCoffee("คาปูชิโน(c4)");
        }else if(n==5){
            setCoffee("มอคค่า(c5)");
        }
        String Coffee = getCoffee();
        return Coffee;
    }

   public String getRandomChanom(){
        int n = rand.nextInt(7);
        if(n==1){
            setChanom("ชานมไต้หวัน(m1)");
        }else if(n==2){
            setChanom("มัทฉะญี่ปุ่น(m2)");
        }else if(n==3){
            setChanom("โกโก้(m3)");
        }else if(n==4){
            setChanom("ชาลาวา(m4)");
        }else if(n==5){
            setChanom("ชาชีส(m5)");
        }else if(n==6){
            setChanom("ชาเขียว(m6)");
        }else if(n==7){
            setChanom("ชาไทย(m7)");
        }
        String Chanom= getChanom();
        return Chanom;
   }

   public String getRandomBeakerry(){
        int n = rand.nextInt(5);
        if(n==1){
            setBeakerry("ชีสเค้ก(d1)");
        }else if(n==2){
            setBeakerry("สตรอว์เบอร์รีชีสเค้ก(d2)");
        }else if(n==3){
            setBeakerry("ทีรามิสุ(d3)");
        }else if(n==4){
            setBeakerry("บราวน์ชูการ์โทสต์(d4)");
        }else if(n==5){
            setBeakerry("เค้กเรดเวลเวท(d5)");
        }
        String Beakerry = getBeakerry();
        return Beakerry;
   }
    
    private void handleTextContent(String replyToken, Event event, TextMessageContent content) {
        
        String text = content.getText();

        log.info("Got text message from %s : %s", replyToken, text);

        switch (checktext(text)) {
            case "profile": {
                String userId = event.getSource().getUserId();
                if(userId != null) {
                    lineMessagingClient.getProfile(userId)
                            .whenComplete((profile, throwable) -> {
                                if(throwable != null) {
                                    this.replyText(replyToken, throwable.getMessage());
                                    return;
                                }
                                this.reply(replyToken, Arrays.asList(
                                        new TextMessage("Display name: " + profile.getDisplayName()),
                                        new TextMessage("Status message: " + profile.getStatusMessage()),
                                        new TextMessage("User ID: " + profile.getUserId())
                                ));
                            });
                }
                break;
            }

            case"อ":{
                if(logi == 1 ){
                    this.replyText(replyToken, getRandomFood());
                    logi = logi - 1 ;
                }else{
                    this.replyText(replyToken, "กรุณาทำรายการให้ถูกต้อง");
                }
            }
            case"ก":{
                if(logi == 1 ){
                    this.replyText(replyToken, getRandomCoffee());
                    logi = logi - 1 ;
                }else{
                    this.replyText(replyToken, "กรุณาทำรายการให้ถูกต้อง");
                }
            }

        
            case"ช":{
                if(logi == 1 ){
                    this.replyText(replyToken, getRandomChanom());
                    logi = logi - 1 ;
                }else{
                    this.replyText(replyToken, "กรุณาทำรายการให้ถูกต้อง");
                }
            }
            case"ข":{
                if(logi == 1){
                    this.replyText(replyToken, getRandomBeakerry());
                    logi = logi - 1 ;
                }else{
                    this.replyText(replyToken, "กรุณาทำรายการให้ถูกต้อง");
                }
            }

            case"ไม่":{
                if(logicc==1){
                this.reply(replyToken, Arrays.asList(
                    new TextMessage("ขอบคุณค่ะ"),
                    new ImageMessage("https://i.ibb.co/VVKC5f3/Mooku.png","https://i.ibb.co/dBhpx87/Mooku.png"),
                    new TextMessage("นี่คือรายการเมนูของทางร้านเราลองพิจารณาดูนะคะ"))
                                
                    );} else {
                        this.reply(replyToken, Arrays.asList(new TextMessage("กรุณาทำรายให้ถูกต้อง")));
                    }
                    logicc = 0;
            }

            case"ใช่":{
                if(logic==1){
                this.reply(replyToken, Arrays.asList(
                                        new TextMessage("อยากกินอะไร???"),
                                        new TextMessage("อ.อาหาร\nก.กาแฟ\nช.ชานม\nข.ขนมหวาน"))
                                        
                
                                ); } else {
                                    this.reply(replyToken, Arrays.asList(new TextMessage("กรุณาทำรายให้ถูกต้อง")));
                                }
                            logic = 0 ;
            }

            case"ไม่รู้จะกินอะไรดี":{
                this.replyText(replyToken, "ให้เราช่วยมั้ย(ใช่/ไม่)");
                logic = 1;
                logicc = 1;
            }

            
            case "order": {
                log.info("You have an order! ");
                this.replyText(replyToken, "สั่งอาหารค้าบบบบ");
            }

            case "ขนมหวาน": {
                this.reply(replyToken, Arrays.asList(
                    new TextMessage("Menu ขนมหวาน"),
                    new TextMessage("ชีสเค้ก(d1)\nราคา 39บาท\nสตรอว์เบอร์รีชีสเค้ก(d2)\nราคา 39บาท\nทีรามิสุ(d3)\nราคา 39บาท\nบราวน์ชูการ์โทสต์(d4)\nราคา 39บาท\nเค้กเรดเวลเวท(d5)\nราคา 39บาท\n")
                
                ));
                
            }
            
            

            case "อาหาร": {
                this.reply(replyToken, Arrays.asList(
                    new TextMessage("Menu อาหาร"),
                    new TextMessage("ไข่กระทะ(f1)\nราคา 40บาท\nมินิพิซซ่าแฮมชีส(f2)\nราคา 59บาท\nแซนด์วิชไก่กรอบ(f3)\nราคา 39บาท\nสลัดไข่เจียว(f4)\nราคา 35บาท\nสเต๊กหมูพันเบคอน(f5)\nราคา 69บาท\nพิเศษ + 10 บาท\n")

                ));
              
            }
            
            case "กาแฟ": {
                this.reply(replyToken, Arrays.asList(
                    new TextMessage("Menu กาแฟ"),
                    new TextMessage("เอสเพรสโซ(c1)\nราคา 45บาท\nอเมริกาโน(c2)\nราคา 45บาท\nลาเต้(c3)\nราคา 45บาท\nคาปูชิโน(c4)\nราคา 45บาท\nมอคค่า(c5)\nราคา 45บาท\n")
                ));
                
            }

            case "ชานม": {
                this.reply(replyToken, Arrays.asList(
                    new TextMessage("Menu ชานม"),
                    new TextMessage("ชานมไต้หวัน(m1)\nราคา 40บาท\nมัทฉะญี่ปุ่น(m2)\nราคา 40บาท\nโกโก้(m3)\nราคา 40บาท\nชาลาวา(m4)\nราคา 40บาท\nชาชีส(m5)\nราคา 40บาท\nชาเขียว(m6)\nราคา 40บาท\nชาไทย(m7)\nราคา 40บาท")
                ));
                
            }

            case "ขอดู d1": {
                this.reply(replyToken, Arrays.asList(
                                        new TextMessage("ชื่อเมนู ชีสเค้ก(d1)"),
                                        new ImageMessage("https://i.ibb.co/Lzd3NF3/cheesecake.jpg","https://i.ibb.co/Lzd3NF3/cheesecake.jpg"),
                                        new TextMessage("รายละเอียด\nชีสเค้กของที่นี่เป็นสูตรพิเศษ รสชาติไม่เหมือนใครแถม ทางร้านเลือกใช้วัตถุดิบพรีเมียม ทำให้เนื้อเค้กที่ได้ออกมามีความเบา เนียนนุ่ม ละลายในปาก ละมุนลิ้นสุดๆ ใครที่ชื่นชอบชีสเค้ก ถ้าได้ลองชิมร้านเรารับรองว่าติดใจ\nราคา 39 บาท\nวีธีสั่ง\nพิมพ์ : สั่ง d1")));
                
            }

            case "ขอดู d2": {
                this.reply(replyToken, Arrays.asList(
                                        new TextMessage("ชื่อเมนู สตรอว์เบอร์รีชีสเค้ก(d2)"),
                                        new ImageMessage("https://i.ibb.co/tprCwby/sto.jpg","https://i.ibb.co/tprCwby/sto.jpg"),
                                        new TextMessage("รายละเอียด\nสตรอว์เบอร์รีชีสเค้กของที่นี่นิ่มแน่นอร่อยยยยยย กินรวมกับตัวซอสเยิ้มๆแล้วคือลงตัวมากกกกกกกก มันเริ่ด ส่วนตัวชีสเค้กเนื้อดีมากกกกกกก ไม่เละไม่เหลว มันแบบชีสแน่นๆกินแล้วหวานมันหอม\nราคา 39 บาท\nวีธีสั่ง\nพิมพ์ : สั่ง d2")));
                
            }

            case "ขอดู d3": {
                this.reply(replyToken, Arrays.asList(
                                        new TextMessage("ชื่อเมนู ทีรามิสุ(d3)"),
                                        new ImageMessage("https://i.ibb.co/5nVx9Qk/teramisu.jpg","https://i.ibb.co/5nVx9Qk/teramisu.jpg"),
                                        new TextMessage("รายละเอียด\nที่มีเลดีฟิงเกอร์จุ่มกาแฟเอสเพรสโซรสชาติขมอมหวานรองเป็นฐาน สลับชั้นไปมากับมาสคาโปเนนุ่ม ๆ ก่อนจะโรยหน้าปิดทับด้วยผงกาแฟ รวมถึงทีรามิสุที่นำมาประยุกต์เป็นรสชาติอื่น ๆ\nราคา 39 บาท\nวีธีสั่ง\nพิมพ์ : สั่ง d3")));
            
            }

            case "ขอดู d4": {
                this.reply(replyToken, Arrays.asList(
                                        new TextMessage("ชื่อเมนู บราวน์ชูการ์โทสต์(d4)"),
                                        new ImageMessage("https://i.ibb.co/0B3gHGp/brownsugar.jpg","https://i.ibb.co/0B3gHGp/brownsugar.jpg"),
                                        new TextMessage("รายละเอียด\nขนมปังโทสต์บราวน์ชูก้าร์ ที่ทางร้านเลือกใช้โทสต์นุ่มๆอบจนกรอบนอก นุ่มใน และยังได้ความกรุบกรอบ พร้อมหอมหวานจากบราวน์ชูก้าร์ที่ฉาบมาบนผิวโทสต์อีกด้วย\nราคา 39 บาท\nวีธีสั่ง\nพิมพ์ : สั่ง d4")));  
            }

            case "ขอดู d5": {
                this.reply(replyToken, Arrays.asList(
                                        new TextMessage("ชื่อเมนู เค้กเรดเวลเวท(d5)"),
                                        new ImageMessage("https://i.ibb.co/Kyh6Gbc/redvelvet.jpg","https://i.ibb.co/Kyh6Gbc/redvelvet.jpg"),
                                        new TextMessage("รายละเอียด\nเค้กสีเเดงสีสันสดใส สลับชั้นกับครีมชีส เนื้อหนาเนื้อนุ่มชุ่มครีม รสชาติอร่อยหวานหอมลงตัว เค้กที่ตัดกันกับครีมชีสรสชาติอมเปรี้ยว\nราคา 39 บาท\nวีธีสั่ง\nพิมพ์ : สั่ง d5")));  
            }

            case "ขอดู f1": { 
                this.reply(replyToken, Arrays.asList(
                                        new TextMessage("ชื่อเมนู ไข่กระทะ(f1)"),
                                        new ImageMessage("https://i.ibb.co/R4NXM9y/eggfly.jpg","https://i.ibb.co/R4NXM9y/eggfly.jpg"),
                                        new TextMessage("รายละเอียด\nมีไข่ 2 ฟอง มาแบบร้อนๆแห้งนิดๆ โรยหน้ามาด้วยหอมทอด หมูยอ หมูกระเพรา ไข่ทำมาแบบสุกๆ ไม่หอมเนย หมูกระเพรารสหวานนำ ไม่เผ็ด ไข่กระทะโดยรวมรสหวานต้องทานกับแมกกี้\nราคา \nธรรมดา  40   บาท\nพิเศษ    50   บาท\nวีธีสั่ง\nพิมพ์ : สั่ง f1 ธรรมดา/พิเศษ")));  
            }

            case "ขอดู f2": { 
                this.reply(replyToken, Arrays.asList(
                                        new TextMessage("ชื่อเมนู มินิพิซซ่าแฮมชีส(f2)"),
                                        new ImageMessage("https://i.ibb.co/CV3BBB3/pizza.jpg","https://i.ibb.co/CV3BBB3/pizza.jpg"),
                                        new TextMessage("รายละเอียด\nมินิพิซซ่าแฮมชีสของทางร้านไม่ใช้แป้งพิซซ่า แต่จะใช้ขนมปังแผ่นแทนและโปะทับด้วย Mozzarella cheeseโรยหน้าด้วยพาสเลห์สับใช้ชีสเหลืองตกแต่งพองามแล้วนำไปอบ\nราคา \nธรรมดา  59   บาท\nพิเศษ    69   บาท\nวีธีสั่ง\nพิมพ์ : สั่ง f2 ธรรมดา/พิเศษ")));  
            }

            case "ขอดู f3": { 
                this.reply(replyToken, Arrays.asList(
                                        new TextMessage("ชื่อเมนู แซนด์วิชไก่กรอบ(f3)"),
                                        new ImageMessage("https://i.ibb.co/qDKCG64/chickensanwich.jpg","https://i.ibb.co/qDKCG64/chickensanwich.jpg"),
                                        new TextMessage("รายละเอียด\nแซนด์วิชไก่กรอบความพิเศษของทางร้านคือ จับไก่ไปอบจนกรอบและราดซอสทงคัตสึ เติมวิตามินจากผักสลัด\nราคา \nธรรมดา  39   บาท\nพิเศษ    49   บาท\nวีธีสั่ง\nพิมพ์ : สั่ง f3 ธรรมดา/พิเศษ")));  
            }

            case "ขอดู f4": { 
                this.reply(replyToken, Arrays.asList(
                                        new TextMessage("ชื่อเมนู สลัดไข่เจียว(f4)"),
                                        new ImageMessage("https://i.ibb.co/h7TgJ2Y/salad.jpg","https://i.ibb.co/h7TgJ2Y/salad.jpg"),
                                        new TextMessage("รายละเอียด\nสลัดไข่เจียวทางร้านเราจะเป็นการจับเมนูไข่มามิกซ์กับเมนูสลัด กลายเป็นสลัดสุดแนว พร้อมสูตรน้ำสลัดพริกไทยดำ\nราคา \nธรรมดา  35   บาท\nพิเศษ    45   บาท\nวีธีสั่ง\nพิมพ์ : สั่ง f4 ธรรมดา/พิเศษ")));  
            }

            case "ขอดู f5": { 
                this.reply(replyToken, Arrays.asList(
                                        new TextMessage("ชื่อเมนู สเต๊กหมูพันเบคอน (f5)"),
                                        new ImageMessage("https://i.ibb.co/z2zKYz8/steak.jpg","https://i.ibb.co/z2zKYz8/steak.jpg"),
                                        new TextMessage("รายละเอียด\nสเต๊กหมูพันเบคอนของทางร้านเราจะเป็นหมูสันในชิ้นใหญ่กว่าที่คิด พันเบคอนมาแบบเต็มๆ ขอเป็นซอสเห็ด มีเครื่องเคียงในจานคือสลัด ผัดผักรวม ขนมปังกระเทียม\nราคา \nธรรมดา  69   บาท\nพิเศษ    79   บาท\nวีธีสั่ง\nพิมพ์ : สั่ง f5 ธรรมดา/พิเศษ")));  
            }

            case "ขอดู m1": { 
                this.reply(replyToken, Arrays.asList(
                                        new TextMessage("ชื่อเมนู ชานมไต้หวัน(m1)"),
                                        new ImageMessage("https://i.ibb.co/kBZ179v/taiwan.jpg","https://i.ibb.co/kBZ179v/taiwan.jpg"),
                                        new TextMessage("รายละเอียด\nชานมไต้หวันของทางร้านจะมีกลิ่นหอมของชาที่ลอยเข้าจมูก เป็นรสชาติที่ละมุนของนม หวานกำลังดี ไม่โดดจนเกินไป ผสมผสานกับความหอมของชาอย่างลงตัว\nราคา\nnormal  40  บาท\nวีธีสั่ง\nพิมพ์ : สั่ง m1x \nx คือระดับความหวาน\n1หวานน้อย\n2หวานปกติ\n3หวานมาก")));  
            }

            case "ขอดู m2": { 
                this.reply(replyToken, Arrays.asList(
                                        new TextMessage("ชื่อเมนู มัทฉะญี่ปุ่น(m2)"),
                                        new ImageMessage("https://i.ibb.co/pjg78vJ/Matcha.jpg","https://i.ibb.co/pjg78vJ/Matcha.jpg"),
                                        new TextMessage("รายละเอียด\nมัทฉะญี่ปุ่นของทางร้านใช้ใบชาแท้ ๆ 100% ไม่แต่งกลิ่นและรสชาติใดๆ ราวกับว่าคุณลูกค้าได้อยู่ประเทศญี่ปุ่นจริง ๆ\nราคา \nnormal  40  บาท\nวีธีสั่ง\nพิมพ์ : สั่ง m2x \nx คือระดับความหวาน\n1หวานน้อย\n2หวานปกติ\n3หวานมาก")));  
            }

            case "ขอดู m3": { 
                this.reply(replyToken, Arrays.asList(
                                        new TextMessage("ชื่อเมนู โกโก้(m3)"),
                                        new ImageMessage("https://i.ibb.co/Jjhgk03/caoco.jpg","https://i.ibb.co/Jjhgk03/caoco.jpg"),
                                        new TextMessage("รายละเอียด\nโกโก้ของทางร้านจะเป็นการนำเมล็ดโกโก้ไปแช่ใน Alkaliเพื่อลดความเป็นกรดของเมล็ด ทำให้โกโก้มีความเข้มข้นและหอมจากนั้นจึงนำเมล็ดไปตากแห้งแล้วนำไปบดละเอียด จนได้กลายมาเป็นผงโกโก้ที่มีค่าความเป็นด่าง\nราคา \nnormal  40  บาท\nวีธีสั่ง\nพิมพ์ : สั่ง m3x \nx คือระดับความหวาน\n1หวานน้อย\n2หวานปกติ\n3หวานมาก")));  
            }

            case "ขอดู m4": { 
                this.reply(replyToken, Arrays.asList(
                                        new TextMessage("ชื่อเมนู ชาลาวา(m4)"),
                                        new ImageMessage("https://i.ibb.co/nkTvg77/lava.jpg","https://i.ibb.co/nkTvg77/lava.jpg"),
                                        new TextMessage("รายละเอียด\nชาลาวาของทางร้านจะเป็นผลไม้ที่ลูกค้าเลือกผสมกับสีของลาวาจากไข่มุกบราวน์ชูก้า ท็อปปิ้งด้วยฟองนมและน่ำตาลทรายแดงพ่นไฟ เพิ่มความหอมกรุ่น\nราคา \nnormal  40  บาท\nวีธีสั่ง\nพิมพ์ : สั่ง m4x \nx คือระดับความหวาน\n1หวานน้อย\n2หวานปกติ\n3หวานมาก")));  
            }

            case "ขอดู m5": { 
                this.reply(replyToken, Arrays.asList(
                                        new TextMessage("ชื่อเมนู ชาชีส(m5)"),
                                        new ImageMessage("https://i.ibb.co/tQssNgb/cheese.jpg","https://i.ibb.co/tQssNgb/cheese.jpg"),
                                        new TextMessage("รายละเอียด\nชาชีสของทางร้านจะเป็นชาจะเป็นชาอูหลงแบบใส แล้วท็อปด้วยครีมชีส ฟองชีสนุ่ม เวลาทานต้องเอียงทำมุม 45 องศา เพื่อให้ได้ส่วนของชาและชีส ก็จะได้รสชาติผสมผสานกัน\nราคา \nnormal  40  บาท\nวีธีสั่ง\nพิมพ์ : สั่ง m5x \nx คือระดับความหวาน\n1หวานน้อย\n2หวานปกติ\n3หวานมาก")));  
            }

            case "ขอดู m6": { 
                this.reply(replyToken, Arrays.asList(
                                        new TextMessage("ชื่อเมนู ชาเขียว(m6)"),
                                        new ImageMessage("https://i.ibb.co/XbHqzsh/greentea.jpg","https://i.ibb.co/XbHqzsh/greentea.jpg"),
                                        new TextMessage("รายละเอียด\nชาเขียวของทางร้านของทางร้านใช้ใบชาแท้ ๆ 100% ไม่แต่งกลิ่นและรสชาติใดๆรสชาติที่ได้ก็จะมีรสขม ๆ แบบกลมกล่อมสไตล์ชาเขียวคุณภาพดี\nราคา \nnormal  40  บาท\nวีธีสั่ง\nพิมพ์ : สั่ง m6x \nx คือระดับความหวาน\n1หวานน้อย\n2หวานปกติ\n3หวานมาก")));  
            }

            case "ขอดู m7": { 
                this.reply(replyToken, Arrays.asList(
                                        new TextMessage("ชื่อเมนู ชาไทย(m7)"),
                                        new ImageMessage("https://i.ibb.co/tb0NCpq/thaitea.jpg","https://i.ibb.co/tb0NCpq/thaitea.jpg"),
                                        new TextMessage("รายละเอียด\nชาไทยของทางร้านจะใช้ชาใต้แท้ 100% เป็นผงชาล้าวนไม่ผสมน้ำตาลหรือครีมเทียมจะมีความเข้มข้น สีส้มสวย หอมชาใต้เน้น ๆ\nราคา \nnormal  40  บาท\nวีธีสั่ง\nพิมพ์ : สั่ง m7x \nx คือระดับความหวาน\n1หวานน้อย\n2หวานปกติ\n3หวานมาก")));  
            }

            case "ขอดู c1": { 
                this.reply(replyToken, Arrays.asList(
                                        new TextMessage("ชื่อเมนู เอสเพรสโซ(c1)"),
                                        new ImageMessage("https://i.ibb.co/4M3y4Rs/espreso.jpg","https://i.ibb.co/4M3y4Rs/espreso.jpg"),
                                        new TextMessage("รายละเอียด\nเอสเพรสโซของทางร้านใช้กาแฟคั่วบดไม่ได้ผ่านการปรุงแต่งใด ๆจึงทำให้แบบคั่วบดจึงได้รสชาติและกลิ่นหอมกรุ่นที่ค่อนข้างพิเศษ\nราคา \nnormal  45  บาท\nวีธีสั่ง\nพิมพ์ : สั่ง c1x \nx คือระดับความหวาน\n1หวานน้อย\n2หวานปกติ\n3หวานมาก")));  
            }

            case "ขอดู c2": { 
                this.reply(replyToken, Arrays.asList(
                                        new TextMessage("ชื่อเมนู อเมริกาโน(c2)"),
                                        new ImageMessage("https://i.ibb.co/ZgKskDh/amaricano.jpg","https://i.ibb.co/ZgKskDh/amaricano.jpg"),
                                        new TextMessage("รายละเอียด\nอเมริกาโนของทางร้านเลือกสายพันธุ์โรบัสต้า จะทำให้รสชาติของอาราบิก้ามีความขมน้อยกว่า แต่จะมีความหวานและเปรี้ยวเพิ่มเติมเข้ามาทดแทน\nราคา \nnormal  45  บาท\nวีธีสั่ง\nพิมพ์ : สั่ง c2x \nx คือระดับความหวาน\n1หวานน้อย\n2หวานปกติ\n3หวานมาก")));  
            }

            case "ขอดู c3": { 
                this.reply(replyToken, Arrays.asList(
                                        new TextMessage("ชื่อเมนู ลาเต้(c3)"),
                                        new ImageMessage("https://i.ibb.co/G2NKG9W/latte.jpg","https://i.ibb.co/G2NKG9W/latte.jpg"),
                                        new TextMessage("รายละเอียด\nลาเต้ของทางร้านทุกแก้วบรรจงทำเป็น Latte Art จิบแล้วเพลินใจ รสชาติเข้มข้นกลมกล่อมไม่ต้องใส่อะไรเพิ่มเลย \nราคา \nnormal  45  บาท\nวีธีสั่ง\nพิมพ์ : สั่ง c3x \nx คือระดับความหวาน\n1หวานน้อย\n2หวานปกติ\n3หวานมาก")));  
            }

            case "ขอดู c4": { 
                this.reply(replyToken, Arrays.asList(
                                        new TextMessage("ชื่อเมนู คาปูชิโน(c4)"),
                                        new ImageMessage("https://i.ibb.co/SsDNVPP/capucino.jpg","https://i.ibb.co/SsDNVPP/capucino.jpg"),
                                        new TextMessage("รายละเอียด\nคาปูชิโนของทางร้านมีรสชาติหวานมันเข้มข้น คาปูเข้มนุ่ม \nราคา \nnormal  45  บาท\nวีธีสั่ง\nพิมพ์ : สั่ง c4x \nx คือระดับความหวาน\n1หวานน้อย\n2หวานปกติ\n3หวานมาก")));  
            }

            case "ขอดู c5": { 
                this.reply(replyToken, Arrays.asList(
                                        new TextMessage("ชื่อเมนู มอคค่า(c5)"),
                                        new ImageMessage("https://i.ibb.co/svG0gRP/mocca.jpg","https://i.ibb.co/svG0gRP/mocca.jpg"),
                                        new TextMessage("รายละเอียด\nมอคค่าของทางร้านมีรสชาติกกลมกล่อมหอมเข้มทั้งกลิ่นกาแฟและโกโก้ผสมผสานกันอย่างลงตัว\nราคา \nnormal  45  บาท\nวีธีสั่ง\nพิมพ์ : สั่ง c5x \nx คือระดับความหวาน\n1หวานน้อย\n2หวานปกติ\n3หวานมาก")));  
            }

            case "สั่ง m11": {
                String userId = event.getSource().getUserId();
                ticket = ticket+1;
                if(userId != null) {
                    lineMessagingClient.getProfile(userId)
                            .whenComplete((profile, throwable) -> {
                                if(throwable != null) {
                                    this.replyText(replyToken, throwable.getMessage());
                                    return;
                                }
                                this.reply(replyToken, Arrays.asList(
                                        new TextMessage("คุณ "+profile.getDisplayName()+"\nคิวของคุณคือ"+ticket+"\nรายการสินค้า ชาใต้หวัน หวานน้อย \n\nรายการ 40 บาท\n\nกรุณารอฟังการเรียกคิว อาหารจะเสร็จภายใน 15 นาที📢📢📢"),
                                        new TextMessage(getThankyou()),
                                        new  StickerMessage("1","4")
                                        
                                ));
                            });
                }
                break;
            }

            case "สั่ง m12": {
                String userId = event.getSource().getUserId();
                ticket = ticket+1;
                if(userId != null) {
                    lineMessagingClient.getProfile(userId)
                            .whenComplete((profile, throwable) -> {
                                if(throwable != null) {
                                    this.replyText(replyToken, throwable.getMessage());
                                    return;
                                }
                                this.reply(replyToken, Arrays.asList(
                                        new TextMessage("คุณ "+profile.getDisplayName()+"\nคิวของคุณคือ"+ticket+"\nรายการสินค้า ชาใต้หวัน หวานปกติ \n\nรายการ 40 บาท\n\nกรุณารอฟังการเรียกคิว อาหารจะเสร็จภายใน 15 นาที📢📢📢"),
                                        new TextMessage(getThankyou()),
                                        new  StickerMessage("1","4")

                                ));
                            });
                }
                break;
            }

            case "สั่ง m13": {
                String userId = event.getSource().getUserId();
                ticket = ticket+1;
                if(userId != null) {
                    lineMessagingClient.getProfile(userId)
                            .whenComplete((profile, throwable) -> {
                                if(throwable != null) {
                                    this.replyText(replyToken, throwable.getMessage());
                                    return;
                                }
                                this.reply(replyToken, Arrays.asList(
                                        new TextMessage("คุณ "+profile.getDisplayName()+"\nคิวของคุณคือ"+ticket+"\nรายการสินค้า ชาใต้หวัน หวานมาก \n\nรายการ 40 บาท\n\nกรุณารอฟังการเรียกคิว อาหารจะเสร็จภายใน 15 นาที📢📢📢"),
                                        new TextMessage(getThankyou()),
                                        new  StickerMessage("1","4")

                                ));
                            });
                }
                break;
            }
            case "สั่ง m21": {
                String userId = event.getSource().getUserId();
                ticket = ticket+1;
                if(userId != null) {
                    lineMessagingClient.getProfile(userId)
                            .whenComplete((profile, throwable) -> {
                                if(throwable != null) {
                                    this.replyText(replyToken, throwable.getMessage());
                                    return;
                                }
                                this.reply(replyToken, Arrays.asList(
                                        new TextMessage("คุณ "+profile.getDisplayName()+"\nคิวของคุณคือ"+ticket+"\nรายการสินค้า มัทฉะญี่ปุ่น หวานน้อย \n\nรายการ 40 บาท\n\nกรุณารอฟังการเรียกคิว อาหารจะเสร็จภายใน 15 นาที📢📢📢"),
                                        new TextMessage(getThankyou()),
                                        new  StickerMessage("1","4")

                                ));
                            });
                }
                break;
            }

            case "สั่ง m22": {
                String userId = event.getSource().getUserId();
                ticket = ticket+1;
                if(userId != null) {
                    lineMessagingClient.getProfile(userId)
                            .whenComplete((profile, throwable) -> {
                                if(throwable != null) {
                                    this.replyText(replyToken, throwable.getMessage());
                                    return;
                                }
                                this.reply(replyToken, Arrays.asList(
                                        new TextMessage("คุณ "+profile.getDisplayName()+"\nคิวของคุณคือ"+ticket+"\nรายการสินค้า มัทฉะญี่ปุ่น หวานปกติ \n\nรายการ 40 บาท\n\nกรุณารอฟังการเรียกคิว อาหารจะเสร็จภายใน 15 นาที📢📢📢"),
                                        new TextMessage(getThankyou()),
                                        new  StickerMessage("1","4")
                                ));
                            });
                }
                break;
            }

            case "สั่ง m23": {
                String userId = event.getSource().getUserId();
                ticket = ticket+1;
                if(userId != null) {
                    lineMessagingClient.getProfile(userId)
                            .whenComplete((profile, throwable) -> {
                                if(throwable != null) {
                                    this.replyText(replyToken, throwable.getMessage());
                                    return;
                                }
                                this.reply(replyToken, Arrays.asList(
                                        new TextMessage("คุณ "+profile.getDisplayName()+"\nคิวของคุณคือ"+ticket+"\nรายการสินค้า มัทฉะญี่ปุ่น หวานมาก \n\nรายการ 40 บาท\n\nกรุณารอฟังการเรียกคิว อาหารจะเสร็จภายใน 15 นาที📢📢📢"),
                                        new TextMessage(getThankyou()),
                                        new  StickerMessage("1","4")

                                ));
                            });
                }
                break;
            }

            case "สั่ง m31": {
                String userId = event.getSource().getUserId();
                ticket = ticket+1;
                if(userId != null) {
                    lineMessagingClient.getProfile(userId)
                            .whenComplete((profile, throwable) -> {
                                if(throwable != null) {
                                    this.replyText(replyToken, throwable.getMessage());
                                    return;
                                }
                                this.reply(replyToken, Arrays.asList(
                                        new TextMessage("คุณ "+profile.getDisplayName()+"\nคิวของคุณคือ"+ticket+"\nรายการสินค้า โกโก้ หวานน้อย \n\nรายการ 40 บาท\n\nกรุณารอฟังการเรียกคิว อาหารจะเสร็จภายใน 15 นาที📢📢📢"),
                                        new TextMessage(getThankyou()),
                                        new  StickerMessage("1","4")

                                ));
                            });
                }
                break;
            }

            case "สั่ง m32": {
                String userId = event.getSource().getUserId();
                ticket = ticket+1;
                if(userId != null) {
                    lineMessagingClient.getProfile(userId)
                            .whenComplete((profile, throwable) -> {
                                if(throwable != null) {
                                    this.replyText(replyToken, throwable.getMessage());
                                    return;
                                }
                                this.reply(replyToken, Arrays.asList(
                                        new TextMessage("คุณ "+profile.getDisplayName()+"\nคิวของคุณคือ"+ticket+"\nรายการสินค้า โกโก้ หวานปกติ \n\nรายการ 40 บาท\n\nกรุณารอฟังการเรียกคิว อาหารจะเสร็จภายใน 15 นาที📢📢📢"),
                                        new TextMessage(getThankyou()),
                                        new  StickerMessage("1","4")

                                ));
                            });
                }
                break;
            }

            case "สั่ง m33": {
                String userId = event.getSource().getUserId();
                ticket = ticket+1;
                if(userId != null) {
                    lineMessagingClient.getProfile(userId)
                            .whenComplete((profile, throwable) -> {
                                if(throwable != null) {
                                    this.replyText(replyToken, throwable.getMessage());
                                    return;
                                }
                                this.reply(replyToken, Arrays.asList(
                                        new TextMessage("คุณ "+profile.getDisplayName()+"\nคิวของคุณคือ"+ticket+"\nรายการสินค้า โกโก้ หวานมาก \n\nรายการ 40 บาท\n\nกรุณารอฟังการเรียกคิว อาหารจะเสร็จภายใน 15 นาที📢📢📢"),
                                        new TextMessage(getThankyou()),
                                        new  StickerMessage("1","4")

                                ));
                            });
                }
                break;
            }

            case "สั่ง m41": {
                String userId = event.getSource().getUserId();
                ticket = ticket+1;
                if(userId != null) {
                    lineMessagingClient.getProfile(userId)
                            .whenComplete((profile, throwable) -> {
                                if(throwable != null) {
                                    this.replyText(replyToken, throwable.getMessage());
                                    return;
                                }
                                this.reply(replyToken, Arrays.asList(
                                        new TextMessage("คุณ "+profile.getDisplayName()+"\nคิวของคุณคือ"+ticket+"\nรายการสินค้า ชาลาวา หวานน้อย \n\nรายการ 40 บาท\n\nกรุณารอฟังการเรียกคิว อาหารจะเสร็จภายใน 15 นาที📢📢📢"),
                                        new TextMessage(getThankyou()),
                                        new  StickerMessage("1","4")

                                ));
                            });
                }
                break;
            }

            case "สั่ง m42": {
                String userId = event.getSource().getUserId();
                ticket = ticket+1;
                if(userId != null) {
                    lineMessagingClient.getProfile(userId)
                            .whenComplete((profile, throwable) -> {
                                if(throwable != null) {
                                    this.replyText(replyToken, throwable.getMessage());
                                    return;
                                }
                                this.reply(replyToken, Arrays.asList(
                                        new TextMessage("คุณ "+profile.getDisplayName()+"\nคิวของคุณคือ"+ticket+"\nรายการสินค้า ชาลาวา หวานปกติ \n\nรายการ 40 บาท\n\nกรุณารอฟังการเรียกคิว อาหารจะเสร็จภายใน 15 นาที📢📢📢"),
                                        new TextMessage(getThankyou()),
                                        new  StickerMessage("1","4")

                                ));
                            });
                }
                break;
            }

            case "สั่ง m43": {
                String userId = event.getSource().getUserId();
                ticket = ticket+1;
                if(userId != null) {
                    lineMessagingClient.getProfile(userId)
                            .whenComplete((profile, throwable) -> {
                                if(throwable != null) {
                                    this.replyText(replyToken, throwable.getMessage());
                                    return;
                                }
                                this.reply(replyToken, Arrays.asList(
                                        new TextMessage("คุณ "+profile.getDisplayName()+"\nคิวของคุณคือ"+ticket+"\nรายการสินค้า ชาลาวา หวานมาก \n\nรายการ 40 บาท\n\nกรุณารอฟังการเรียกคิว อาหารจะเสร็จภายใน 15 นาที📢📢📢"),
                                        new TextMessage(getThankyou()),
                                        new  StickerMessage("1","4")

                                ));
                            });
                }
                break;
            }
            case "สั่ง m51": {
                String userId = event.getSource().getUserId();
                ticket = ticket+1;
                if(userId != null) {
                    lineMessagingClient.getProfile(userId)
                            .whenComplete((profile, throwable) -> {
                                if(throwable != null) {
                                    this.replyText(replyToken, throwable.getMessage());
                                    return;
                                }
                                this.reply(replyToken, Arrays.asList(
                                        new TextMessage("คุณ "+profile.getDisplayName()+"\nคิวของคุณคือ"+ticket+"\nรายการสินค้า ชาชีส หวานน้อย \n\nรายการ 40 บาท\n\nกรุณารอฟังการเรียกคิว อาหารจะเสร็จภายใน 15 นาที📢📢📢"),
                                        new TextMessage(getThankyou()),
                                        new  StickerMessage("1","4")

                                ));
                            });
                }
                break;
            }

            case "สั่ง m52": {
                String userId = event.getSource().getUserId();
                ticket = ticket+1;
                if(userId != null) {
                    lineMessagingClient.getProfile(userId)
                            .whenComplete((profile, throwable) -> {
                                if(throwable != null) {
                                    this.replyText(replyToken, throwable.getMessage());
                                    return;
                                }
                                this.reply(replyToken, Arrays.asList(
                                        new TextMessage("คุณ "+profile.getDisplayName()+"\nคิวของคุณคือ"+ticket+"\nรายการสินค้า ชาชีส หวานปกติ \n\nรายการ 40 บาท\n\nกรุณารอฟังการเรียกคิว อาหารจะเสร็จภายใน 15 นาที📢📢📢"),
                                        new TextMessage(getThankyou()),
                                        new  StickerMessage("1","4")

                                ));
                            });
                }
                break;
            }

            case "สั่ง m53": {
                String userId = event.getSource().getUserId();
                ticket = ticket+1;
                if(userId != null) {
                    lineMessagingClient.getProfile(userId)
                            .whenComplete((profile, throwable) -> {
                                if(throwable != null) {
                                    this.replyText(replyToken, throwable.getMessage());
                                    return;
                                }
                                this.reply(replyToken, Arrays.asList(
                                        new TextMessage("คุณ "+profile.getDisplayName()+"\nคิวของคุณคือ"+ticket+"\nรายการสินค้า ชาชีส หวานมาก \n\nรายการ 40 บาท\n\nกรุณารอฟังการเรียกคิว อาหารจะเสร็จภายใน 15 นาที📢📢📢"),
                                        new TextMessage(getThankyou()),
                                        new  StickerMessage("1","4")

                                ));
                            });
                }
                break;
            }
            case "สั่ง m61": {
                String userId = event.getSource().getUserId();
                ticket = ticket+1;
                if(userId != null) {
                    lineMessagingClient.getProfile(userId)
                            .whenComplete((profile, throwable) -> {
                                if(throwable != null) {
                                    this.replyText(replyToken, throwable.getMessage());
                                    return;
                                }
                                this.reply(replyToken, Arrays.asList(
                                        new TextMessage("คุณ "+profile.getDisplayName()+"\nคิวของคุณคือ"+ticket+"\nรายการสินค้า ชาเขียว หวานน้อย \n\nรายการ 40 บาท\n\nกรุณารอฟังการเรียกคิว อาหารจะเสร็จภายใน 15 นาที📢📢📢"),
                                        new TextMessage(getThankyou()),
                                        new  StickerMessage("1","4")

                                ));
                            });
                }
                break;
            }

            case "สั่ง m62": {
                String userId = event.getSource().getUserId();
                ticket = ticket+1;
                if(userId != null) {
                    lineMessagingClient.getProfile(userId)
                            .whenComplete((profile, throwable) -> {
                                if(throwable != null) {
                                    this.replyText(replyToken, throwable.getMessage());
                                    return;
                                }
                                this.reply(replyToken, Arrays.asList(
                                        new TextMessage("คุณ "+profile.getDisplayName()+"\nคิวของคุณคือ"+ticket+"\nรายการสินค้า ชาเขียว หวานปกติ \n\nรายการ 40 บาท\n\nกรุณารอฟังการเรียกคิว อาหารจะเสร็จภายใน 15 นาที📢📢📢"),
                                        new TextMessage(getThankyou()),
                                        new  StickerMessage("1","4")

                                ));
                            });
                }
                break;
            }

            case "สั่ง m63": {
                String userId = event.getSource().getUserId();
                ticket = ticket+1;
                if(userId != null) {
                    lineMessagingClient.getProfile(userId)
                            .whenComplete((profile, throwable) -> {
                                if(throwable != null) {
                                    this.replyText(replyToken, throwable.getMessage());
                                    return;
                                }
                                this.reply(replyToken, Arrays.asList(
                                        new TextMessage("คุณ "+profile.getDisplayName()+"\nคิวของคุณคือ"+ticket+"\nรายการสินค้า ชาเขียว หวานมาก \n\nรายการ 40 บาท\n\nกรุณารอฟังการเรียกคิว อาหารจะเสร็จภายใน 15 นาที📢📢📢"),
                                        new TextMessage(getThankyou()),
                                        new  StickerMessage("1","4")

                                ));
                            });
                }
                break;
            }

            case "สั่ง m71": {
                String userId = event.getSource().getUserId();
                ticket = ticket+1;
                if(userId != null) {
                    lineMessagingClient.getProfile(userId)
                            .whenComplete((profile, throwable) -> {
                                if(throwable != null) {
                                    this.replyText(replyToken, throwable.getMessage());
                                    return;
                                }
                                this.reply(replyToken, Arrays.asList(
                                        new TextMessage("คุณ "+profile.getDisplayName()+"\nคิวของคุณคือ"+ticket+"\nรายการสินค้า ชาไทย หวานน้อย \n\nรายการ 40 บาท\n\nกรุณารอฟังการเรียกคิว อาหารจะเสร็จภายใน 15 นาที📢📢📢"),
                                        new TextMessage(getThankyou()),
                                        new  StickerMessage("1","4")

                                ));
                            });
                }
                break;
            }

            case "สั่ง m72": {
                String userId = event.getSource().getUserId();
                ticket = ticket+1;
                if(userId != null) {
                    lineMessagingClient.getProfile(userId)
                            .whenComplete((profile, throwable) -> {
                                if(throwable != null) {
                                    this.replyText(replyToken, throwable.getMessage());
                                    return;
                                }
                                this.reply(replyToken, Arrays.asList(
                                        new TextMessage("คุณ "+profile.getDisplayName()+"\nคิวของคุณคือ"+ticket+"\nรายการสินค้า ชาไทย หวานปกติ \n\nรายการ 40 บาท\n\nกรุณารอฟังการเรียกคิว อาหารจะเสร็จภายใน 15 นาที📢📢📢"),
                                        new TextMessage(getThankyou()),
                                        new  StickerMessage("1","4")
                                ));
                            });
                }
                break;
            }

            case "สั่ง m73": {
                String userId = event.getSource().getUserId();
                ticket = ticket+1;
                if(userId != null) {
                    lineMessagingClient.getProfile(userId)
                            .whenComplete((profile, throwable) -> {
                                if(throwable != null) {
                                    this.replyText(replyToken, throwable.getMessage());
                                    return;
                                }
                                this.reply(replyToken, Arrays.asList(
                                        new TextMessage("คุณ "+profile.getDisplayName()+"\nคิวของคุณคือ"+ticket+"\nรายการสินค้า ชาไทย หวานมาก \n\nรายการ 40 บาท\n\nกรุณารอฟังการเรียกคิว อาหารจะเสร็จภายใน 15 นาที📢📢📢"),
                                        new TextMessage(getThankyou()),
                                        new  StickerMessage("1","4")

                                ));
                            });
                }
                break;
            }

            case "สั่ง c11": {
                String userId = event.getSource().getUserId();
                ticket = ticket+1;
                if(userId != null) {
                    lineMessagingClient.getProfile(userId)
                            .whenComplete((profile, throwable) -> {
                                if(throwable != null) {
                                    this.replyText(replyToken, throwable.getMessage());
                                    return;
                                }
                                this.reply(replyToken, Arrays.asList(
                                        new TextMessage("คุณ "+profile.getDisplayName()+"\nคิวของคุณคือ"+ticket+"\nรายการสินค้า เอสเพรสโซ หวานน้อย \n\nรายการ 45 บาท\n\nกรุณารอฟังการเรียกคิว อาหารจะเสร็จภายใน 15 นาที📢📢📢"),
                                        new TextMessage(getThankyou()),
                                        new  StickerMessage("1","4")

                                ));
                            });
                }
                break;
            }

            case "สั่ง c12": {
                String userId = event.getSource().getUserId();
                ticket = ticket+1;
                if(userId != null) {
                    lineMessagingClient.getProfile(userId)
                            .whenComplete((profile, throwable) -> {
                                if(throwable != null) {
                                    this.replyText(replyToken, throwable.getMessage());
                                    return;
                                }
                                this.reply(replyToken, Arrays.asList(
                                        new TextMessage("คุณ "+profile.getDisplayName()+"\nคิวของคุณคือ"+ticket+"\nรายการสินค้า เอสเพรสโซ หวานปกติ \n\nรายการ 45 บาท\n\nกรุณารอฟังการเรียกคิว อาหารจะเสร็จภายใน 15 นาที📢📢📢"),
                                        new TextMessage(getThankyou()),
                                        new  StickerMessage("1","4")

                                ));
                            });
                }
                break;
            }

            case "สั่ง c13": {
                String userId = event.getSource().getUserId();
                ticket = ticket+1;
                if(userId != null) {
                    lineMessagingClient.getProfile(userId)
                            .whenComplete((profile, throwable) -> {
                                if(throwable != null) {
                                    this.replyText(replyToken, throwable.getMessage());
                                    return;
                                }
                                this.reply(replyToken, Arrays.asList(
                                        new TextMessage("คุณ "+profile.getDisplayName()+"\nคิวของคุณคือ"+ticket+"\nรายการสินค้า เอสเพรสโซ หวานมาก \n\nรายการ 45 บาท\n\nกรุณารอฟังการเรียกคิว อาหารจะเสร็จภายใน 15 นาที📢📢📢"),
                                        new TextMessage(getThankyou()),
                                        new  StickerMessage("1","4")

                                ));
                            });
                }
                break;
            }

            case "สั่ง c21": {
                String userId = event.getSource().getUserId();
                ticket = ticket+1;
                if(userId != null) {
                    lineMessagingClient.getProfile(userId)
                            .whenComplete((profile, throwable) -> {
                                if(throwable != null) {
                                    this.replyText(replyToken, throwable.getMessage());
                                    return;
                                }
                                this.reply(replyToken, Arrays.asList(
                                        new TextMessage("คุณ "+profile.getDisplayName()+"\nคิวของคุณคือ"+ticket+"\nรายการสินค้า อเมริกาโน หวานน้อย \n\nรายการ 45 บาท\n\nกรุณารอฟังการเรียกคิว อาหารจะเสร็จภายใน 15 นาที📢📢📢"),
                                        new TextMessage(getThankyou()),
                                        new  StickerMessage("1","4")

                                ));
                            });
                }
                break;
            }

            case "สั่ง c22": {
                String userId = event.getSource().getUserId();
                ticket = ticket+1;
                if(userId != null) {
                    lineMessagingClient.getProfile(userId)
                            .whenComplete((profile, throwable) -> {
                                if(throwable != null) {
                                    this.replyText(replyToken, throwable.getMessage());
                                    return;
                                }
                                this.reply(replyToken, Arrays.asList(
                                        new TextMessage("คุณ "+profile.getDisplayName()+"\nคิวของคุณคือ"+ticket+"\nรายการสินค้า อเมริกาโน หวานปกติ \n\nรายการ 45 บาท\n\nกรุณารอฟังการเรียกคิว อาหารจะเสร็จภายใน 15 นาที📢📢📢"),
                                        new TextMessage(getThankyou()),
                                        new  StickerMessage("1","4")

                                ));
                            });
                }
                break;
            }

            case "สั่ง c23": {
                String userId = event.getSource().getUserId();
                ticket = ticket+1;
                if(userId != null) {
                    lineMessagingClient.getProfile(userId)
                            .whenComplete((profile, throwable) -> {
                                if(throwable != null) {
                                    this.replyText(replyToken, throwable.getMessage());
                                    return;
                                }
                                this.reply(replyToken, Arrays.asList(
                                        new TextMessage("คุณ "+profile.getDisplayName()+"\nคิวของคุณคือ"+ticket+"\nรายการสินค้า อเมริกาโน หวานมาก \n\nรายการ 45 บาท\n\nกรุณารอฟังการเรียกคิว อาหารจะเสร็จภายใน 15 นาที📢📢📢"),
                                        new TextMessage(getThankyou()),
                                        new  StickerMessage("1","4")

                                ));
                            });
                }
                break;
            }

            case "สั่ง c31": {
                String userId = event.getSource().getUserId();
                ticket = ticket+1;
                if(userId != null) {
                    lineMessagingClient.getProfile(userId)
                            .whenComplete((profile, throwable) -> {
                                if(throwable != null) {
                                    this.replyText(replyToken, throwable.getMessage());
                                    return;
                                }
                                this.reply(replyToken, Arrays.asList(
                                        new TextMessage("คุณ "+profile.getDisplayName()+"\nคิวของคุณคือ"+ticket+"\nรายการสินค้า ลาเต้ หวานน้อย \n\nรายการ 45 บาท\n\nกรุณารอฟังการเรียกคิว อาหารจะเสร็จภายใน 15 นาที📢📢📢"),
                                        new TextMessage(getThankyou()),
                                        new  StickerMessage("1","4")

                                ));
                            });
                }
                break;
            }

            case "สั่ง c32": {
                String userId = event.getSource().getUserId();
                ticket = ticket+1;
                if(userId != null) {
                    lineMessagingClient.getProfile(userId)
                            .whenComplete((profile, throwable) -> {
                                if(throwable != null) {
                                    this.replyText(replyToken, throwable.getMessage());
                                    return;
                                }
                                this.reply(replyToken, Arrays.asList(
                                        new TextMessage("คุณ "+profile.getDisplayName()+"\nคิวของคุณคือ"+ticket+"\nรายการสินค้า ลาเต้ หวานปกติ \n\nรายการ 45 บาท\n\nกรุณารอฟังการเรียกคิว อาหารจะเสร็จภายใน 15 นาที📢📢📢"),
                                        new TextMessage(getThankyou()),
                                        new  StickerMessage("1","4")

                                ));
                            });
                }
                break;
            }

            case "สั่ง c33": {
                String userId = event.getSource().getUserId();
                ticket = ticket+1;
                if(userId != null) {
                    lineMessagingClient.getProfile(userId)
                            .whenComplete((profile, throwable) -> {
                                if(throwable != null) {
                                    this.replyText(replyToken, throwable.getMessage());
                                    return;
                                }
                                this.reply(replyToken, Arrays.asList(
                                        new TextMessage("คุณ "+profile.getDisplayName()+"\nคิวของคุณคือ"+ticket+"\nรายการสินค้า ลาเต้ หวานมาก \n\nรายการ 45 บาท\n\nกรุณารอฟังการเรียกคิว อาหารจะเสร็จภายใน 15 นาที📢📢📢"),
                                        new TextMessage(getThankyou()),
                                        new  StickerMessage("1","4")

                                ));
                            });
                }
                break;
            }

            case "สั่ง c41": {
                String userId = event.getSource().getUserId();
                ticket = ticket+1;
                if(userId != null) {
                    lineMessagingClient.getProfile(userId)
                            .whenComplete((profile, throwable) -> {
                                if(throwable != null) {
                                    this.replyText(replyToken, throwable.getMessage());
                                    return;
                                }
                                this.reply(replyToken, Arrays.asList(
                                        new TextMessage("คุณ "+profile.getDisplayName()+"\nคิวของคุณคือ"+ticket+"\nรายการสินค้า คาปูชิโน หวานน้อย \n\nรายการ 45 บาท\n\nกรุณารอฟังการเรียกคิว อาหารจะเสร็จภายใน 15 นาที📢📢📢"),
                                        new TextMessage(getThankyou()),
                                        new  StickerMessage("1","4")

                                ));
                            });
                }
                break;
            }

            case "สั่ง c42": {
                String userId = event.getSource().getUserId();
                ticket = ticket+1;
                if(userId != null) {
                    lineMessagingClient.getProfile(userId)
                            .whenComplete((profile, throwable) -> {
                                if(throwable != null) {
                                    this.replyText(replyToken, throwable.getMessage());
                                    return;
                                }
                                this.reply(replyToken, Arrays.asList(
                                        new TextMessage("คุณ "+profile.getDisplayName()+"\nคิวของคุณคือ"+ticket+"\nรายการสินค้า คาปูชิโน หวานปกติ \n\nรายการ 45 บาท\n\nกรุณารอฟังการเรียกคิว อาหารจะเสร็จภายใน 15 นาที📢📢📢"),
                                        new TextMessage(getThankyou()),
                                        new  StickerMessage("1","4")

                                ));
                            });
                }
                break;
            }

            case "สั่ง c43": {
                String userId = event.getSource().getUserId();
                ticket = ticket+1;
                if(userId != null) {
                    lineMessagingClient.getProfile(userId)
                            .whenComplete((profile, throwable) -> {
                                if(throwable != null) {
                                    this.replyText(replyToken, throwable.getMessage());
                                    return;
                                }
                                this.reply(replyToken, Arrays.asList(
                                        new TextMessage("คุณ "+profile.getDisplayName()+"\nคิวของคุณคือ"+ticket+"\nรายการสินค้า คาปูชิโน หวานมาก \n\nรายการ 45 บาท\n\nกรุณารอฟังการเรียกคิว อาหารจะเสร็จภายใน 15 นาที📢📢📢"),
                                        new TextMessage(getThankyou()),
                                        new  StickerMessage("1","4")

                                ));
                            });
                }
                break;
            }

            case "สั่ง c51": {
                String userId = event.getSource().getUserId();
                ticket = ticket+1;
                if(userId != null) {
                    lineMessagingClient.getProfile(userId)
                            .whenComplete((profile, throwable) -> {
                                if(throwable != null) {
                                    this.replyText(replyToken, throwable.getMessage());
                                    return;
                                }
                                this.reply(replyToken, Arrays.asList(
                                        new TextMessage("คุณ "+profile.getDisplayName()+"\nคิวของคุณคือ"+ticket+"\nรายการสินค้า มอคค่า หวานน้อย \n\nรายการ 45 บาท\n\nกรุณารอฟังการเรียกคิว อาหารจะเสร็จภายใน 15 นาที📢📢📢"),
                                        new TextMessage(getThankyou()),
                                        new  StickerMessage("1","4")

                                ));
                            });
                }
                break;
            }

            case "สั่ง c52": {
                String userId = event.getSource().getUserId();
                ticket = ticket+1;
                if(userId != null) {
                    lineMessagingClient.getProfile(userId)
                            .whenComplete((profile, throwable) -> {
                                if(throwable != null) {
                                    this.replyText(replyToken, throwable.getMessage());
                                    return;
                                }
                                this.reply(replyToken, Arrays.asList(
                                        new TextMessage("คุณ "+profile.getDisplayName()+"\nคิวของคุณคือ"+ticket+"\nรายการสินค้า มอคค่า หวานปกติ \n\nรายการ 45 บาท\n\nกรุณารอฟังการเรียกคิว อาหารจะเสร็จภายใน 15 นาที📢📢📢"),
                                        new TextMessage(getThankyou()),
                                        new  StickerMessage("1","4")

                                ));
                            });
                }
                break;
            }

            case "สั่ง c53": {
                String userId = event.getSource().getUserId();
                ticket = ticket+1;
                if(userId != null) {
                    lineMessagingClient.getProfile(userId)
                            .whenComplete((profile, throwable) -> {
                                if(throwable != null) {
                                    this.replyText(replyToken, throwable.getMessage());
                                    return;
                                }
                                this.reply(replyToken, Arrays.asList(
                                        new TextMessage("คุณ "+profile.getDisplayName()+"\nคิวของคุณคือ"+ticket+"\nรายการสินค้า มอคค่า หวานมาก \n\nรายการ 45 บาท\n\nกรุณารอฟังการเรียกคิว อาหารจะเสร็จภายใน 15 นาที📢📢📢"),
                                        new TextMessage(getThankyou()),
                                        new  StickerMessage("1","4")

                                ));
                            });
                }
                break;
            }
            
            case "สั่ง d1": {
                String userId = event.getSource().getUserId();
                ticket = ticket+1;
                if(userId != null) {
                    lineMessagingClient.getProfile(userId)
                            .whenComplete((profile, throwable) -> {
                                if(throwable != null) {
                                    this.replyText(replyToken, throwable.getMessage());
                                    return;
                                }
                                this.reply(replyToken, Arrays.asList(
                                        new TextMessage("คุณ "+profile.getDisplayName()+"\nคิวของคุณคือ"+ticket+"\nรายการสินค้า ชีสเค้ก\n\nรายการ 39 บาท\n\nกรุณารอฟังการเรียกคิว อาหารจะเสร็จภายใน 15 นาที📢📢📢"),
                                        
                                        new TextMessage(getThankyou()),
                                        new  StickerMessage("1","4")

                                ));
                            });
                }
                break;
            }

            case "สั่ง d2": {
                String userId = event.getSource().getUserId();
                ticket = ticket+1;
                if(userId != null) {
                    lineMessagingClient.getProfile(userId)
                            .whenComplete((profile, throwable) -> {
                                if(throwable != null) {
                                    this.replyText(replyToken, throwable.getMessage());
                                    return;
                                }
                                this.reply(replyToken, Arrays.asList(
                                        new TextMessage("คุณ "+profile.getDisplayName()+"\nคิวของคุณคือ"+ticket+"\nรายการสินค้า สตรอว์เบอร์รีชีสเค้ก\n\nรายการ 39 บาท\n\nกรุณารอฟังการเรียกคิว อาหารจะเสร็จภายใน 15 นาที📢📢📢"),
                                    
                                        new TextMessage(getThankyou()),
                                        new  StickerMessage("1","4")

                                ));
                            });
                }
                break;
            }

            case "สั่ง d3": {
                String userId = event.getSource().getUserId();
                ticket = ticket+1;
                if(userId != null) {
                    lineMessagingClient.getProfile(userId)
                            .whenComplete((profile, throwable) -> {
                                if(throwable != null) {
                                    this.replyText(replyToken, throwable.getMessage());
                                    return;
                                }
                                this.reply(replyToken, Arrays.asList(
                                        new TextMessage("คุณ "+profile.getDisplayName()+"\nคิวของคุณคือ"+ticket+"\nรายการสินค้า ทีรามิสุ\n\nรายการ 39 บาท\n\nกรุณารอฟังการเรียกคิว อาหารจะเสร็จภายใน 15 นาที📢📢📢"),
                                        new TextMessage(getThankyou()),
                                        new  StickerMessage("1","4")

                                ));
                            });
                }
                break;
            }

            case "สั่ง d4": {
                String userId = event.getSource().getUserId();
                ticket = ticket+1;
                if(userId != null) {
                    lineMessagingClient.getProfile(userId)
                            .whenComplete((profile, throwable) -> {
                                if(throwable != null) {
                                    this.replyText(replyToken, throwable.getMessage());
                                    return;
                                }
                                this.reply(replyToken, Arrays.asList(
                                        new TextMessage("คุณ "+profile.getDisplayName()+"\nคิวของคุณคือ"+ticket+"\nรายการสินค้า บราวน์ชูการ์โทสต์\n\nรายการ 39 บาท\n\nกรุณารอฟังการเรียกคิว อาหารจะเสร็จภายใน 15 นาที📢📢📢"),
                                        new TextMessage(getThankyou()),
                                        new  StickerMessage("1","4")

                                ));
                            });
                }
                break;
            }

            case "สั่ง d5": {
                String userId = event.getSource().getUserId();
                ticket = ticket+1;
                if(userId != null) {
                    lineMessagingClient.getProfile(userId)
                            .whenComplete((profile, throwable) -> {
                                if(throwable != null) {
                                    this.replyText(replyToken, throwable.getMessage());
                                    return;
                                }
                                this.reply(replyToken, Arrays.asList(
                                        new TextMessage("คุณ "+profile.getDisplayName()+"\nคิวของคุณคือ"+ticket+"\nรายการสินค้า เค้กเรดเวลเวท\n\nรายการ 39 บาท\n\nกรุณารอฟังการเรียกคิว อาหารจะเสร็จภายใน 15 นาที📢📢📢"),
                                        new TextMessage(getThankyou()),
                                        new  StickerMessage("1","4")

                                ));
                            });
                }
                break;
            }
            
            case "สั่ง f1 ธรรมดา": {
                String userId = event.getSource().getUserId();
                ticket = ticket+1;
                if(userId != null) {
                    lineMessagingClient.getProfile(userId)
                            .whenComplete((profile, throwable) -> {
                                if(throwable != null) {
                                    this.replyText(replyToken, throwable.getMessage());
                                    return;
                                }
                                this.reply(replyToken, Arrays.asList(
                                        new TextMessage("คุณ "+profile.getDisplayName()+"\nคิวของคุณคือ"+ticket+"\nรายการสินค้า ไข่กระทะ ธรรมดา\n\nรายการ 40 บาท\n\nกรุณารอฟังการเรียกคิว อาหารจะเสร็จภายใน 15 นาที📢📢📢"),
                                        new TextMessage(getThankyou()),
                                        new  StickerMessage("1","4")

                                ));
                            });
                }
                break;
            }

            case "สั่ง f1 พิเศษ": {
                String userId = event.getSource().getUserId();
                ticket = ticket+1;
                if(userId != null) {
                    lineMessagingClient.getProfile(userId)
                            .whenComplete((profile, throwable) -> {
                                if(throwable != null) {
                                    this.replyText(replyToken, throwable.getMessage());
                                    return;
                                }
                                this.reply(replyToken, Arrays.asList(
                                        new TextMessage("คุณ "+profile.getDisplayName()+"\nคิวของคุณคือ"+ticket+"\nรายการสินค้า ไข่กระทะ พิเศษ \n\nรายการ 50 บาท\n\nกรุณารอฟังการเรียกคิว อาหารจะเสร็จภายใน 15 นาที📢📢📢"),
                                        new TextMessage(getThankyou()),
                                        new  StickerMessage("1","4")

                                ));
                            });
                }
                break;
            }

            case "สั่ง f2 ธรรมดา": {
                String userId = event.getSource().getUserId();
                ticket = ticket+1;
                if(userId != null) {
                    lineMessagingClient.getProfile(userId)
                            .whenComplete((profile, throwable) -> {
                                if(throwable != null) {
                                    this.replyText(replyToken, throwable.getMessage());
                                    return;
                                }
                                this.reply(replyToken, Arrays.asList(
                                        new TextMessage("คุณ "+profile.getDisplayName()+"\nคิวของคุณคือ"+ticket+"\nรายการสินค้า มินิพิซซ่าแฮมชีส\n\nรายการ 59 บาท\n\nกรุณารอฟังการเรียกคิว อาหารจะเสร็จภายใน 15 นาที📢📢📢"),
                                        new TextMessage(getThankyou()),
                                        new  StickerMessage("1","4")

                                ));
                            });
                }
                break;
            }

            case "สั่ง f2 พิเศษ": {
                String userId = event.getSource().getUserId();
                ticket = ticket+1;
                if(userId != null) {
                    lineMessagingClient.getProfile(userId)
                            .whenComplete((profile, throwable) -> {
                                if(throwable != null) {
                                    this.replyText(replyToken, throwable.getMessage());
                                    return;
                                }
                                this.reply(replyToken, Arrays.asList(
                                        new TextMessage("คุณ "+profile.getDisplayName()+"\nคิวของคุณคือ"+ticket+"\nรายการสินค้า มินิพิซซ่าแฮมชีส พิเศษ\n\nรายการ 69 บาท\n\nกรุณารอฟังการเรียกคิว อาหารจะเสร็จภายใน 15 นาที📢📢📢"),
                                        new TextMessage(getThankyou()),
                                        new  StickerMessage("1","4")

                                ));
                            });
                }
                break;
            }
            case "สั่ง f3 ธรรมดา": {
                String userId = event.getSource().getUserId();
                ticket = ticket+1;
                if(userId != null) {
                    lineMessagingClient.getProfile(userId)
                            .whenComplete((profile, throwable) -> {
                                if(throwable != null) {
                                    this.replyText(replyToken, throwable.getMessage());
                                    return;
                                }
                                this.reply(replyToken, Arrays.asList(
                                        new TextMessage("คุณ "+profile.getDisplayName()+"\nคิวของคุณคือ"+ticket+"\nรายการสินค้า แซนด์วิชไก่กรอบ\n\nรายการ 39 บาท\n\nกรุณารอฟังการเรียกคิว อาหารจะเสร็จภายใน 15 นาที📢📢📢"),
                                        new TextMessage(getThankyou()),
                                        new  StickerMessage("1","4")

                                ));
                            });
                }
                break;
            }

            case "สั่ง f3 พิเศษ": {
                String userId = event.getSource().getUserId();
                ticket = ticket+1;
                if(userId != null) {
                    lineMessagingClient.getProfile(userId)
                            .whenComplete((profile, throwable) -> {
                                if(throwable != null) {
                                    this.replyText(replyToken, throwable.getMessage());
                                    return;
                                }
                                this.reply(replyToken, Arrays.asList(
                                        new TextMessage("คุณ "+profile.getDisplayName()+"\nคิวของคุณคือ"+ticket+"\nรายการสินค้า แซนด์วิชไก่กรอบ พิเศษ\n\nรายการ 49 บาท\n\nกรุณารอฟังการเรียกคิว อาหารจะเสร็จภายใน 15 นาที📢📢📢"),
                                        new TextMessage(getThankyou()),
                                        new  StickerMessage("1","4")

                                ));
                            });
                }
                break;
            }

            case "สั่ง f4 ธรรมดา": {
                String userId = event.getSource().getUserId();
                ticket = ticket+1;
                if(userId != null) {
                    lineMessagingClient.getProfile(userId)
                            .whenComplete((profile, throwable) -> {
                                if(throwable != null) {
                                    this.replyText(replyToken, throwable.getMessage());
                                    return;
                                }
                                this.reply(replyToken, Arrays.asList(
                                        new TextMessage("คุณ "+profile.getDisplayName()+"\nคิวของคุณคือ"+ticket+"\nรายการสินค้า สลัดไข่เจียว\n\nรายการ 35 บาท\n\nกรุณารอฟังการเรียกคิว อาหารจะเสร็จภายใน 15 นาที📢📢📢"),
                                        new TextMessage(getThankyou()),
                                        new  StickerMessage("1","4")

                                ));
                            });
                }
                break;
            }

            case "สั่ง f4 พิเศษ": {
                String userId = event.getSource().getUserId();
                ticket = ticket+1;
                if(userId != null) {
                    lineMessagingClient.getProfile(userId)
                            .whenComplete((profile, throwable) -> {
                                if(throwable != null) {
                                    this.replyText(replyToken, throwable.getMessage());
                                    return;
                                }
                                this.reply(replyToken, Arrays.asList(
                                        new TextMessage("คุณ "+profile.getDisplayName()+"\nคิวของคุณคือ"+ticket+"\nรายการสินค้า สลัดไข่เจียว พิเศษ\n\nรายการ 45 บาท\n\nกรุณารอฟังการเรียกคิว อาหารจะเสร็จภายใน 15 นาที📢📢📢"),
                                        new TextMessage(getThankyou()),
                                        new  StickerMessage("1","4")

                                ));
                            });
                }
                break;
            }

            case "สั่ง f5 ธรรมดา": {
                String userId = event.getSource().getUserId();
                ticket = ticket+1;
                if(userId != null) {
                    lineMessagingClient.getProfile(userId)
                            .whenComplete((profile, throwable) -> {
                                if(throwable != null) {
                                    this.replyText(replyToken, throwable.getMessage());
                                    return;
                                }
                                this.reply(replyToken, Arrays.asList(
                                        new TextMessage("คุณ "+profile.getDisplayName()+"\nคิวของคุณคือ"+ticket+"\nรายการสินค้า สเต๊กหมูพันเบคอน\n\nรายการ 69 บาท\n\nกรุณารอฟังการเรียกคิว อาหารจะเสร็จภายใน 15 นาที📢📢📢"),
                                        new TextMessage(getThankyou()),
                                        new  StickerMessage("1","4") 

                                ));
                            });
                }
                break;
            }

            case "สั่ง f5 พิเศษ": {
                String userId = event.getSource().getUserId();
                ticket = ticket+1;
                if(userId != null) {
                    lineMessagingClient.getProfile(userId)
                            .whenComplete((profile, throwable) -> {
                                if(throwable != null) {
                                    this.replyText(replyToken, throwable.getMessage());
                                    return;
                                }
                                this.reply(replyToken, Arrays.asList(
                                        new TextMessage("คุณ "+profile.getDisplayName()+"\nคิวของคุณคือ"+ticket+"\nรายการสินค้า สเต๊กหมูพันเบคอน\n\nรายการ 79 บาท\n\nกรุณารอฟังการเรียกคิว อาหารจะเสร็จภายใน 15 นาที📢📢📢"),
                                        new TextMessage(getThankyou()),
                                        new  StickerMessage("1","4")

                                ));
                            });
                }
                break;
            }
            case "สวัสดี": {
                this.reply(replyToken, Arrays.asList(
                                        new TextMessage("สวัสดีค่ะ รับอะไรดีคะเลือกหมวดหมู่ตามรูปได้เลยค่ะ"),
                                        new ImageMessage("https://i.ibb.co/VVKC5f3/Mooku.png","https://i.ibb.co/dBhpx87/Mooku.png")));
                                    }
            case "เมนู": {this.reply(replyToken, Arrays.asList(
                                        new TextMessage("สวัสดีค่ะ รับอะไรดีคะเลือกหมวดหมู่ตามรูปได้เลยค่ะ"),
                                        new ImageMessage("https://i.ibb.co/VVKC5f3/Mooku.png","https://i.ibb.co/VVKC5f3/Mooku.png"),
                                        new ImageMessage("https://i.ibb.co/Nyq5rH6/1.jpg","https://i.ibb.co/Nyq5rH6/1.jpg"),
                                        new ImageMessage("https://i.ibb.co/4T6mHKY/2.jpg","https://i.ibb.co/4T6mHKY/2.jpg")
                                        ));
                                    }                                  

            case "โอเค": {
                this.reply(replyToken, Arrays.asList(
                                        new TextMessage("ขอบคุณที่มาใช้บริการของทางร้าน Sometea"),
                                        new  StickerMessage("1","13")));
                                    }

            case "ลำคาน": {
                this.reply(replyToken, Arrays.asList(
                                        new TextMessage("ทางเราต้องขอโทษคุณลูกค้าที่ทำให้เกิดเรื่องแบบนี้นะคะ"),
                                        new  StickerMessage("1","9")));
                                    }

            

            case "อ่าว": {
                this.replyText(replyToken, "มีปัญหาอะไรหรือป่าวคะ");
            }

            case "อห": {
                this.replyText(replyToken, "มีปัญหาอะไรหรือป่าวคะ");
            }

            case "อ่าว อห": {
                this.replyText(replyToken, "มีปัญหาอะไรหรือป่าวคะ");
            }

           

            case "ลืมวิธีใช้งานอ่ะ": {
                this.reply(replyToken, Arrays.asList(
                                        new ImageMessage("https://i.ibb.co/Nyq5rH6/1.jpg","https://i.ibb.co/Nyq5rH6/1.jpg"),
                                        new ImageMessage("https://i.ibb.co/4T6mHKY/2.jpg","https://i.ibb.co/4T6mHKY/2.jpg")
                                       
                                        ));
                                    }

            default:
                log.info("Return uncommand message %s : %s", replyToken, text);
                this.replyText(replyToken, "ขออภัย ทางร้านเราไม่ได้มีคำสั่งนี้\nคุณลูกค้าสามารถพิมพ์คำสั่ง 'เมนู' เพื่อดูวิธีการใช้งาน");

        }
    }

    private void handleStickerContent(String replyToken, StickerMessageContent content) {
        reply(replyToken, new StickerMessage(
                content.getPackageId(), content.getStickerId()
        ));
    }

    private void replyText(@NonNull  String replyToken, @NonNull String message) {
        if(replyToken.isEmpty()) {
            throw new IllegalArgumentException("replyToken is not empty");
        }

        if(message.length() > 1000) {
            message = message.substring(0, 1000 - 2) + "...";
        }
        this.reply(replyToken, new TextMessage(message));
    }

    private void reply(@NonNull String replyToken, @NonNull Message message) {
        reply(replyToken, Collections.singletonList(message));
    }

    private void reply(@NonNull String replyToken, @NonNull List<Message> messages) {
        try {
            BotApiResponse response = lineMessagingClient.replyMessage(
                    new ReplyMessage(replyToken, messages)
            ).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    private void system(String... args) {
        ProcessBuilder processBuilder = new ProcessBuilder(args);
        try {
            Process start = processBuilder.start();
            int i = start.waitFor();
            log.info("result: {} => {}", Arrays.toString(args), i);
        } catch (InterruptedException e) {
            log.info("Interrupted", e);
            Thread.currentThread().interrupt();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private static DownloadedContent saveContent(String ext, MessageContentResponse response) {
        log.info("Content-type: {}", response);
        DownloadedContent tempFile = createTempFile(ext);
        try (OutputStream outputStream = Files.newOutputStream(tempFile.path)) {
            ByteStreams.copy(response.getStream(), outputStream);
            log.info("Save {}: {}", ext, tempFile);
            return tempFile;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private static DownloadedContent createTempFile(String ext) {
        String fileName = LocalDateTime.now() + "-" + UUID.randomUUID().toString() + "." + ext;
        Path tempFile = Application.downloadedContentDir.resolve(fileName);
        tempFile.toFile().deleteOnExit();
        return new DownloadedContent(tempFile, createUri("/downloaded/" + tempFile.getFileName()));

    }

    private static String createUri(String path) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(path).toUriString();
    }

    @Value
    public static class DownloadedContent {
        Path path;
        String uri;
    }
}