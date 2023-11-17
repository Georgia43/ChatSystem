package Model;

import java.net.InetAddress;

public class User {

        /*attributs*/
        private String nickname;
        private InetAddress id;

        /*méthodes*/
        public String getNickname() {return this.nickname;}
        public InetAddress getId() {return this.id;}


        //set pseudo
        public void setNickname(String name) {
            this.nickname= name;
        }
        //set id
        public void setId(InetAddress id) {
            this.id= id;
        }
        @Override
        public String toString() {
                return "User{"
                        + "username='" + nickname + '\''
                        + ", id='" + id + '\''
                        + '}';
        }


}
