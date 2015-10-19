package vb.smarthome.routerAPI.driver;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vb.smarthome.routerAPI.entity.DHCPClient;


/**
 * @author Bazint
 */
@Service
public class ClientDriver {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private WebDriver webDriver;
	
	private String baseUrl;
	
	  
	public ClientDriver(String routerURL, WebDriver webDriver) {
//		driver = new HtmlUnitDriver(true);
//	    baseUrl = "http://tplinklogin.net/";
		baseUrl = routerURL;
		
	    webDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}
	
	
	public List<DHCPClient> getDHCPClientList() {
		logger.info("Fetching DHCP clients");
		webDriver.get(baseUrl);
	    webDriver.findElement(By.id("pcPassword")).clear();
	    webDriver.findElement(By.id("pcPassword")).sendKeys("admin");
	    webDriver.findElement(By.id("loginBtn")).click();
	    
	    String title = webDriver.getTitle();
	    logger.debug("Page title:" + title);
	    
	    
	    WebDriver menuFrame1 = webDriver.switchTo().frame(webDriver.findElement(By.id("frame1")));
	    WebElement findElement = webDriver.findElement(By.id("menu"));
		findElement.findElement(By.id("menu_dhcp")).click();
	    webDriver.findElement(By.id("menu_dhcpclient")).click();
	    logger.debug("DHCP menu opened");
		
	    webDriver.switchTo().defaultContent();
	    WebDriver menuFrame2 = webDriver.switchTo().frame(webDriver.findElement(By.id("frame2")));
	    webDriver.findElement(By.id("updateBtn")).click();
	    logger.debug("DHCP clients updated");
	    
	    
	    WebElement dhcpClientsTable = webDriver.findElement(By.tagName("tbody"));
	    List<WebElement> dhcpClientsTableRows = dhcpClientsTable.findElements(By.tagName("tr"));
	    
	    List<DHCPClient> dhcpClients = new ArrayList<DHCPClient>();
	    
	    for (WebElement webElement : dhcpClientsTableRows) {
			List<WebElement> dhcpClientsTableCols = webElement.findElements(By.tagName("td"));
	    	DHCPClient client = new DHCPClient();
	    	
	    	client.setId(Integer.parseInt(dhcpClientsTableCols.get(0).getText().trim()));
	    	client.setName(dhcpClientsTableCols.get(1).getText().trim());
	    	client.setMacAddress(dhcpClientsTableCols.get(2).getText().trim());
	    	client.setAssignedIP(dhcpClientsTableCols.get(3).getText().trim());
	    	client.setLeaseTime(dhcpClientsTableCols.get(4).getText().trim());
	    	dhcpClients.add(client);
	    	logger.info(client.getId() + " " + client.getName() + " " + client.getMacAddress() + " "
	    			+ client.getAssignedIP() + " " + client.getLeaseTime());
		}
	    return dhcpClients;
	}
}
