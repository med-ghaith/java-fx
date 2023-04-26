package esprit.changemakers.sportshub.entities.Modulegestionsalle;

import java.time.LocalDate;

public class Subscription {
	private int id;
	private int member_id;
	private int gym_id;
	private LocalDate start_date;
	private String validity;

	public Subscription() {
		super();
	}

	public Subscription(int id, int member_id, int gym_id, LocalDate start_date, String validity) {
		super();
		this.id = id;
		this.member_id = member_id;
		this.gym_id = gym_id;
		this.start_date = start_date;
		this.validity = validity;
	}

	public Subscription(int member_id, int gym_id, LocalDate start_date, String validity) {
		super();
		this.member_id = member_id;
		this.gym_id = gym_id;
		this.start_date = start_date;
		this.validity = validity;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getMember_id() {
		return member_id;
	}

	public void setMember_id(int member_id) {
		this.member_id = member_id;
	}

	public int getGym_id() {
		return gym_id;
	}

	public void setGym_id(int gym_id) {
		this.gym_id = gym_id;
	}

	public LocalDate getStart_date() {
		return start_date;
	}

	public void setStart_date(LocalDate start_date) {
		this.start_date = start_date;
	}

	public String getValidity() {
		return validity;
	}

	public void setValidity(String validity) {
		this.validity = validity;
	}

	@Override
	public String toString() {
		return "Subscription [id=" + id + ", member_id=" + member_id + ", gym_id=" + gym_id
				+ ", start_date=" + start_date + ", validity=" + validity + "]";
	}

}
