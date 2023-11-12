package Model;

public class User {

        /*attributs*/
        private String nickname;
        private String id;

        /*méthodes*/
        public String getNickname() {return this.nickname;}
        public String getId() {return this.id;}


        //set pseudo
        public void setNickname(String name) {
            this.nickname= name;
        }
        //set id
        public void setId(String id) {
            this.id= id;
        }


}
