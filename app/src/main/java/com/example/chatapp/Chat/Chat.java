package com.example.chatapp.Chat;

public class Chat {
    String senderid;
    String receiverid;
    String msg;
    String receiverimg;
    String sendername;
    String receivername;
    String id;
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Chat(String s, String r, String msg, String receiverimg, String sendername, String receivername) {
        this.senderid = s;
        this.receiverid = r;
        this.msg = msg;
        this.receiverimg = receiverimg;
        this.sendername = sendername;
        this.receivername = receivername;
    }

    //    public Chat(String s, String r,String sendername,String receivername){
//        this.senderid=s;
//        this.receiverid=r;
//        this.sendername=sendername;
//        this.receivername=receivername;
//    }
//    public Chat(String s, String r, String msg, String receiverimg,String sendername,String receivername,String id){
//        this.senderid=s;
//        this.receiverid=r;
//        this.msg=msg;
//        this.receiverimg=receiverimg;
//        this.sendername=sendername;
//        this.receivername=receivername;
//    }
    public Chat() {
    }

    public String getSenderid() {
        return senderid;
    }

    public void setSenderid(String senderid) {
        this.senderid = senderid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReceiverid() {
        return receiverid;
    }

    public void setReceiverid(String receiverid) {
        this.receiverid = receiverid;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getReceiverimg() {
        return receiverimg;
    }

    public void setReceiverimg(String receiverimg) {
        this.receiverimg = receiverimg;
    }

    public String getSendername() {
        return sendername;
    }

    public void setSendername(String sendername) {
        this.sendername = sendername;
    }

    public String getReceivername() {
        return receivername;
    }

    public void setReceivername(String receivername) {
        this.receivername = receivername;
    }
}
