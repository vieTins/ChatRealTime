/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

/**
 *
 * @author FPTSHOP
 */
// quản lý các sự kiện của các thành phần khác nhau trong giao diện người dùng 
// chỉ có một thể hiển duy nhất được tạo ra     
    public class PublicEnvent {
        private static PublicEnvent instance ;
        public EventMain eventMain ;
        private EventImageView eventImageView ;
        private EventChat eventChat ;
        private EventLogin eventLogin ;
        private EventMenuLeft eventMenuLeft ;
        private EventMessage eventMessage ;
        private EventRegister eventRegister ;
    //  Lấy instance 
        public static PublicEnvent getInstance() {
            if (instance == null) {
                instance = new PublicEnvent() ;
            }
            return instance;
        }
        private PublicEnvent() { 
        }    
        public void addEventImageView(EventImageView eventImageView) {
            this.eventImageView = eventImageView ;
        }
        public EventImageView getEventImageView() {
            return eventImageView;
        }
        public void addEventChat (EventChat eventChat) {
           this.eventChat = eventChat  ;
        }

        public EventChat getEventChat() {
            return eventChat;
        }
        public void addEventLogin(EventLogin eventLogin) {
            this.eventLogin = eventLogin ;
        }

        public EventLogin getEventLogin() {
            return eventLogin;
        }
        public void addEventMain (EventMain eventMain) {
            this.eventMain = eventMain ;
        }
        public EventMain getEventMain() {
            return eventMain;
        }
        public void adddEventMenuLeft (EventMenuLeft event ) {
            this.eventMenuLeft = event;
        }

    public EventMenuLeft getEventMenuLeft() {
        return eventMenuLeft;
    }

    public EventMessage getEventMessage() {
        return eventMessage;
    }

    public EventRegister getEventRegister() {
        return eventRegister;
    }
    public void addEventRegister(EventRegister eventRegister) {
        this.eventRegister = eventRegister;
    }
    }
