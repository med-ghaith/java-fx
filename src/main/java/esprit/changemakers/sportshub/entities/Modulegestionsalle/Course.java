package esprit.changemakers.sportshub.entities.Modulegestionsalle;


public class Course {
	private int gym_id;
	private int id;
	private String name;
	private String description;
	private String video;


	public Course() {
		super();
	}

	public Course(int id, int gym_id, String name, String description, String video) {
		super();
		this.gym_id = gym_id;
		this.id = id;
		this.name = name;
		this.description = description;
		this.video = video;

	}

	public Course(int gym_id, String name, String description, String video) {
		super();
		this.gym_id = gym_id;
		this.name = name;
		this.description = description;
		this.video = video;
	}

	public int getGym_id() {
		return gym_id;
	}

	public void setGym_id(int gym_id) {
		this.gym_id = gym_id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}

	@Override
	public String toString() {
		return "Course{" +
				"gym_id=" + gym_id +
				", id=" + id +
				", name='" + name + '\'' +
				", description='" + description + '\'' +
				", video=" + video +
				'}';
	}
}
