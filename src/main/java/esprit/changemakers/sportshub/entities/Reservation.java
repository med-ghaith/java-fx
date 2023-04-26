package esprit.changemakers.sportshub.entities;

public class Reservation {

        private int id_reservation;
        private int id_event;
        private int id_user;
        private String status;

        public Reservation(int id_reservation, int id_event, int id_user, String status) {
            this.id_reservation = id_reservation;
            this.id_event = id_event;
            this.id_user = id_user;
            this.status = status;
        }

        public Reservation(int id_event, int id_user, String status) {
            this.id_event = id_event;
            this.id_user = id_user;
            this.status = status;
        }

        public Reservation() {

        }

        public int getId_reservation() {
            return id_reservation;
        }

        public void setId_reservation(int id_reservation) {
            this.id_reservation = id_reservation;
        }

        public int getId_event() {
            return id_event;
        }

        public void setId_event(int id_event) {
            this.id_event = id_event;
        }

        public int getId_user() {
            return id_user;
        }

        public void setId_user(int id_user) {
            this.id_user = id_user;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        @Override
        public String toString() {
            return "Reservation{" +
                    "id_reservation=" + id_reservation +
                    ", id_event=" + id_event +
                    ", id_user=" + id_user +
                    ", status='" + status + '\'' +
                    '}';
        }
}
