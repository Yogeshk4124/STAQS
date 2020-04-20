package com.example.chatapp.Chat;

public class Chat {
    String sender;
    String receiver;
    String msg;
    String receiverimg;

    public Chat(String s,String r,String msg,String receiverimg){
        this.sender=s;
        this.receiver=r;
        this.msg=msg;
        this.receiverimg=receiverimg;
    }
    public Chat(){

    }
    public String getReceiverimg() {
        return receiverimg;
    }

    public void setReceiverimg(String receiverimg) {
        this.receiverimg = receiverimg;
    }

    public String getSenderimg() {
        return senderimg;
    }

    public void setSenderimg(String senderimg) {
        this.senderimg = senderimg;
    }

    String senderimg;

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
