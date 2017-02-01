package cherry.crmhandlers.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cherry.crmhandlers.service.AWSTableManagement;




// TEST TO HANDLE TABLE FROM SERVER
// TO BE CONTINUED ?
@RestController
@RequestMapping("/table")
public class AWSTableManagementController {
	

	
	@RequestMapping("/scan")
	public void scanTable(@RequestParam(value="adress", required = false) String a_str) throws Exception {
		
		AWSTableManagement.scanTable();
	
	}
}
