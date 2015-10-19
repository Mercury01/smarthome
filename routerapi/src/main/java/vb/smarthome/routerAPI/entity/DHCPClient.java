package vb.smarthome.routerAPI.entity;

/**
 * @author Bazint
 */
public class DHCPClient {
	private int id;
	private String name;
	private String macAddress;
	private String assignedIP;
	private String leaseTime;
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the macAddress
	 */
	public String getMacAddress() {
		return macAddress;
	}
	/**
	 * @param macAddress the macAddress to set
	 */
	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}
	/**
	 * @return the assignedIP
	 */
	public String getAssignedIP() {
		return assignedIP;
	}
	/**
	 * @param assignedIP the assignedIP to set
	 */
	public void setAssignedIP(String assignedIP) {
		this.assignedIP = assignedIP;
	}
	/**
	 * @return the leaseTime
	 */
	public String getLeaseTime() {
		return leaseTime;
	}
	/**
	 * @param leaseTime the leaseTime to set
	 */
	public void setLeaseTime(String leaseTime) {
		this.leaseTime = leaseTime;
	}
	
	
}
