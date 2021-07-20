package happyhome.model;

public class Favorite {

	protected int favoriteId;
	protected User user;
	protected County county;
	
	public Favorite(int favoriteId, User user, County county) {
		this.favoriteId = favoriteId;
		this.user = user;
		this.county = county;
	}
	
	public Favorite(int favoriteId) {
		this.favoriteId = favoriteId;
	}
	
	public Favorite(User user, County county) {
		this.user = user;
		this.county = county;
	}

	public int getFavoriteId() {
		return favoriteId;
	}

	public void setFavoriteId(int favoriteId) {
		this.favoriteId = favoriteId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public County getCounty() {
		return county;
	}

	public void setCounty(County county) {
		this.county = county;
	}
	
	
}
