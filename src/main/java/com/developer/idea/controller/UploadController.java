package com.developer.idea.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import com.developer.idea.PlateModel.CsvModel;
import com.developer.idea.service.FileStorageService;
import com.developer.idea.util.ReponseData;
import com.developer.idea.util.ReportFirstModel;
import com.developer.idea.util.ReportSecondModel;
import com.developer.idea.util.ResponseWrapper;
import com.developer.idea.util.Utility;

@RestController
@CrossOrigin
public class UploadController {

	@Autowired
	private FileStorageService fileStorageService;
	
	@Autowired
	Environment env;

	@PostMapping("/upload")
	public ResponseEntity<ResponseWrapper> uploadCSVFile(@RequestParam("file") MultipartFile file, Model model)
			throws IOException {
		String fileName = fileStorageService.storeFile(file);
		List<CsvModel> csvData= Utility.csvMapper(env.getProperty("csvfile"));
		return new ResponseEntity<>(new ResponseWrapper(true, "Plate is valid", csvData), HttpStatus.ACCEPTED);
	}

	@GetMapping("/calculate")
	public ResponseEntity<ResponseWrapper> getCalculate() throws SAXException, IOException {

		List<String> csvRows = null;
		try (Stream<String> reader = Files.lines(Paths.get("uploads/"+env.getProperty("csvfile")))) {
			csvRows = reader.collect(Collectors.toList());
		} catch (Exception e) {
			e.printStackTrace();
		}

		String json = "";
		if (csvRows != null) {
			json = Utility.csvToJson(csvRows);
			System.out.println(json);
			JSONArray jsonarray = new JSONArray(json);
			List<String> list = new ArrayList<String>();
			for (int i = 0; i < jsonarray.length(); i++) {
				JSONObject jsonobject = jsonarray.getJSONObject(i);

				// jsonobject.length()
				System.out.println(jsonobject.length());
				for (int j = 0; j < jsonobject.length() - 1; j++) {
					list.add(jsonarray.getJSONObject(i).getString("A" + (j + 1)));
				}
			}

			System.out.println("List " + list.size());
			System.out.println("List of items " + list);
			BigDecimal NC1;
			BigDecimal NC2;
			BigDecimal PC1;
			BigDecimal PC2;
			BigDecimal NCx;
			BigDecimal PCx;
			String divideBy = "2";
			BigDecimal divisor = new BigDecimal(divideBy);

			System.out.println(list.get(0));

			NC1 = new BigDecimal(list.get(0));
			NC2 = new BigDecimal(list.get(1));
			System.out.println("NC1 and NC2 values " + NC1 + " " + NC2);

			PC1 = new BigDecimal(list.get(2));
			PC2 = new BigDecimal(list.get(3));
			System.out.println("PC1 and PC2 values " + PC1 + " " + PC2);

			NCx = NC1.add(NC2).divide(divisor);
			PCx = PC1.add(PC2).divide(divisor);

			System.out.println("NCx value " + NCx);
			System.out.println("PCx value " + PCx);

			System.out.println("validate values");

			BigDecimal d = PCx.subtract(NCx);
			System.out.println("PCx-NCx value " + d);
			BigDecimal c1 = new BigDecimal("0.075");
			BigDecimal c2 = new BigDecimal("0.150");

			int v = d.compareTo(c1);
			System.out.println("PCx-NCx > 0.075 " + v);
			int vd = NCx.compareTo(c2);
			System.out.println("NCx <= 0.150 " + v);

			BigDecimal sp = BigDecimal.ZERO;

			List<BigDecimal> sPList = new ArrayList<BigDecimal>();
			List<Double> logList = new ArrayList<Double>();

			if ((v == 1) && (vd == -1 || vd == 0)) {

				// new code start
				List<ReportFirstModel> finalList = new ArrayList<ReportFirstModel>();
				List<ReportSecondModel> sendReport = new ArrayList<ReportSecondModel>();
				ReportSecondModel rr = new ReportSecondModel("AMean", 0.043, 0.043, 0.043);
				sendReport.add(rr);

				String sample[] = { "Neg", "Neg", "Pos", "Pos" };
				String alpha[] = { "A1", "A2", "A3", "A4", "A5", "A6", "A7", "A8", "A9", "A10", "A11", "A12", "B1",
						"B2", "B3", "B4", "B5", "B6", "B7", "B8", "B9", "B10", "B11", "B12", "C1", "C2", "C3", "C4",
						"C5", "C6", "C7", "C8", "C9", "C10", "C11", "C12", "D1", "D2", "D3", "D4", "D5", "D6", "D7",
						"D8", "D9", "D10", "D11", "D12", "E1", "E2", "E3", "E4", "E5", "E6", "E7", "E8", "E9", "E10",
						"E11", "E12", "F1", "F2", "F3", "F4", "F5", "F6", "F7", "F8", "F9", "F10", "F11", "F12", "G1",
						"G2", "G3", "G4", "G5", "G6", "G7", "G8", "G9", "G10", "G11", "G12", "H1", "H2", "H3", "H4",
						"H5", "H6", "H7", "H8", "H9", "H10", "H11", "H12", };
				ReportFirstModel fm;
				int sa = 1;
				int logTiter = 0;
				String res = "";
				long group=0;
				
				
				
				
				
				

				for (int f = 0; f < list.size(); f++) {
					String s = "";
					if (f < 4) {
						s = sample[f];
					} else {
						s = Integer.toString(sa);
						sa++;
					}

					if (f > 3) {
						BigDecimal sampleMean = new BigDecimal(list.get(f));
						sp = sampleMean.subtract(NCx).divide(d,RoundingMode.HALF_UP);

						logTiter = (int)Math.pow(10,1.09 * Math.log10(sp.doubleValue()) + 3.36);
                        group=Utility.findIndex(Math.round(logTiter));
						BigDecimal co = new BigDecimal("0.20");
						int resD = sp.compareTo(co);
						if (resD == 1) {
							res = "Pos";
						} else {
							res = "Neg";
						}
					}

					fm = new ReportFirstModel(s, alpha[f], list.get(f), sp, Math.round(logTiter), group, res);
					finalList.add(fm);

				}

				System.out.println("Final List " + finalList);

				List<ReponseData> res1 = new ArrayList<ReponseData>();
				ReponseData r1 = new ReponseData();
				r1.setData(finalList);
				r1.setLabel("Report1");
				ReponseData r2 = new ReponseData();
				r2.setData(sendReport);
				r2.setLabel("Report2");
				res1.add(r1);
				res1.add(r2);
				System.out.println(res1);

				return new ResponseEntity<>(new ResponseWrapper(true, "Plate is valid", res1), HttpStatus.ACCEPTED);

			} else {
				System.out.println("Value is not valid");
				return new ResponseEntity<>(new ResponseWrapper(false, "Plate is not valid", null), HttpStatus.OK);
			}

		}

		return new ResponseEntity<>(new ResponseWrapper(false, "", null), HttpStatus.ACCEPTED);

	}

}
