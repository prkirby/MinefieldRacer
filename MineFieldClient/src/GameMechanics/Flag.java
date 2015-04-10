package GameMechanics;

/**
 * This class represents a flag
 * @author 
 *
 */
public class Flag extends Entity{
	private boolean isPlaced = false;
	
	/**
	 * Denotes if a given flag has been placed
	 * @return
	 * 			If the flag has been placed
	 */
	public boolean getIsPlaced() {
		return isPlaced;
	}
	
	/**
	 * Sets if the flag has been placed
	 * @param isPlaced
	 * 			The status of the flag placement
	 */
	public void setIsPlaced(boolean isPlaced) {
		this.isPlaced = isPlaced;
	}
}
