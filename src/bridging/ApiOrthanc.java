package bridging;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fungsi.koneksiDB;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author windiartonugroho
 */
public class ApiOrthanc {
    private HttpHeaders headers ;
    private JsonNode root,rootx;
    private HttpEntity requestEntity;
    private ObjectMapper mapper = new ObjectMapper();
    private HttpComponentsClientHttpRequestFactory factory;
    private RestTemplate restTemplate;
    private String auth,authEncrypt,requestJson,requestJson1;
    private volatile String lastError="";
    private byte[] encodedBytes;
    private int i=1;
    
    public ApiOrthanc(){
        try {
            auth=koneksiDB.USERORTHANC()+":"+koneksiDB.PASSORTHANC();
            encodedBytes = Base64.encodeBase64(auth.getBytes());
            authEncrypt= new String(encodedBytes);
        } catch (Exception ex) {
            System.out.println("Notifikasi : "+ex);
        }
    }
    
    public String Auth(){
        return authEncrypt;
    }

    public String getLastError(){
        return lastError;
    }

    private void tampilkanPesan(String pesan){
        if(SwingUtilities.isEventDispatchThread()){
            JOptionPane.showMessageDialog(null,pesan);
        }else{
            SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(null,pesan));
        }
    }
    
    public JsonNode AmbilSeries(String Norm,String Tanggal1,String Tanggal2){
        lastError="";
        root=mapper.createArrayNode();
        try{
            headers = new HttpHeaders();
            headers.add("Authorization", "Basic "+authEncrypt);
            requestJson = "{"+
                              "\"Level\": \"Study\","+
                              "\"Expand\": true,"+
                              "\"Query\": {"+
                                   "\"StudyDate\": \""+Tanggal1+"-"+Tanggal2+"\","+
                                   "\"PatientID\": \"*"+Norm+"\""+
                              "}"+
                          "}";
            requestEntity = new HttpEntity(requestJson,headers);
            requestJson=getRest().exchange(koneksiDB.URLORTHANC()+":"+koneksiDB.PORTORTHANC()+"/tools/find", HttpMethod.POST, requestEntity, String.class).getBody();            
            root = mapper.readTree(requestJson);
        }catch(Exception e){
            lastError=e.getMessage()==null ? e.getClass().getSimpleName() : e.getMessage();
            root=mapper.createArrayNode();
            System.out.println("Orthanc AmbilSeries gagal : "+e.getClass().getSimpleName());
        }
        return root;
    }
    
    public JsonNode AmbilPhoto(String Norm,String Tanggal1,String Tanggal2){
        lastError="";
        root=mapper.createArrayNode();
        try{
            headers = new HttpHeaders();
            headers.add("Authorization", "Basic "+authEncrypt);
            requestJson = "{"+
                              "\"Level\": \"Study\","+
                              "\"Expand\": true,"+
                              "\"Query\": {"+
                                   "\"StudyDate\": \""+Tanggal1+"-"+Tanggal2+"\","+
                                   "\"PatientID\": \"*"+Norm+"\""+
                              "}"+
                          "}";
            requestEntity = new HttpEntity(requestJson,headers);
            requestJson=getRest().exchange(koneksiDB.URLORTHANC()+":"+koneksiDB.PORTORTHANC()+"/tools/find", HttpMethod.POST, requestEntity, String.class).getBody();            
            root = mapper.readTree(requestJson);
            for(JsonNode list:root){
                for(JsonNode sublist:list.path("Series")){
            headers = new HttpHeaders();
            headers.add("Authorization", "Basic "+authEncrypt);
            requestEntity = new HttpEntity(headers);
            requestJson=getRest().exchange(koneksiDB.URLORTHANC()+":"+koneksiDB.PORTORTHANC()+"/series/"+sublist.asText(), HttpMethod.GET, requestEntity, String.class).getBody();
            root = mapper.readTree(requestJson);
                }
            }
        }catch(Exception e){
            lastError=e.getMessage()==null ? e.getClass().getSimpleName() : e.getMessage();
            root=mapper.createArrayNode();
            System.out.println("Orthanc AmbilPhoto gagal : "+e.getClass().getSimpleName());
        }
        return root;
    }
    
    public JsonNode AmbilInstances(String seri){
        lastError="";
        root=mapper.createObjectNode();
        try{
            headers = new HttpHeaders();
            headers.add("Authorization", "Basic "+authEncrypt);
            requestEntity = new HttpEntity(headers);
            requestJson=getRest().exchange(koneksiDB.URLORTHANC()+":"+koneksiDB.PORTORTHANC()+"/series/"+seri, HttpMethod.GET, requestEntity, String.class).getBody();
            root = mapper.readTree(requestJson);
        }catch(Exception e){
            lastError=e.getMessage()==null ? e.getClass().getSimpleName() : e.getMessage();
            root=mapper.createObjectNode();
            System.out.println("Orthanc AmbilInstances gagal : "+e.getClass().getSimpleName());
        }
        return root;
    }
    
    public JsonNode AmbilPng(String NoRawat,String Series){
        lastError="";
        root=mapper.createObjectNode();
        try{
            headers = new HttpHeaders();
            headers.add("Authorization", "Basic "+authEncrypt);
            requestEntity = new HttpEntity(headers);
            requestJson=getRest().exchange(koneksiDB.URLORTHANC()+":"+koneksiDB.PORTORTHANC()+"/series/"+Series, HttpMethod.GET, requestEntity, String.class).getBody();
            root = mapper.readTree(requestJson);
            i=1;
            for(JsonNode list:root.path("Instances")){
                 headers = new HttpHeaders();
                 headers.add("Authorization", "Basic "+authEncrypt);
                 headers.add("Accept","image/png");
                 headers.setAccept(Collections.singletonList(MediaType.APPLICATION_OCTET_STREAM));
                 headers.setAccept(Collections.singletonList(MediaType.IMAGE_JPEG));
                 HttpEntity<String> entity = new HttpEntity<>(headers);
                 ResponseEntity<byte[]> response = getRest().exchange(koneksiDB.URLORTHANC()+":"+koneksiDB.PORTORTHANC()+"/instances/"+list.asText()+"/preview", HttpMethod.GET, entity, byte[].class);
                 Files.write(Paths.get("./gambarradiologi/"+NoRawat+i+".png"),response.getBody());
                 i++;
            }
            tampilkanPesan("Pengambilan Gambar PNG dari Orthanc berhasil, silahkan lihat di dalam folder Aplikasi..!!");
        }catch(Exception e){
            lastError=e.getMessage()==null ? e.getClass().getSimpleName() : e.getMessage();
            root=mapper.createObjectNode();
            System.out.println("Orthanc AmbilPng gagal : "+e.getClass().getSimpleName());
            tampilkanPesan("Gagal mengambil Gambar PNG dari Orthanc, silahkan hubungi administrator ..!!");
        }
        return root;
    }
    
    public JsonNode AmbilJpg(String NoRawat,String Series){
        lastError="";
        root=mapper.createObjectNode();
        try{
            headers = new HttpHeaders();
            headers.add("Authorization", "Basic "+authEncrypt);
            requestEntity = new HttpEntity(headers);
            requestJson=getRest().exchange(koneksiDB.URLORTHANC()+":"+koneksiDB.PORTORTHANC()+"/series/"+Series, HttpMethod.GET, requestEntity, String.class).getBody();
            root = mapper.readTree(requestJson);
            i=1;
            for(JsonNode list:root.path("Instances")){
                 headers = new HttpHeaders();
                 headers.add("Authorization", "Basic "+authEncrypt);
                 headers.add("Accept","image/jpeg");
                 headers.setAccept(Collections.singletonList(MediaType.APPLICATION_OCTET_STREAM));
                 headers.setAccept(Collections.singletonList(MediaType.IMAGE_JPEG));
                 HttpEntity<String> entity = new HttpEntity<>(headers);
                 ResponseEntity<byte[]> response = getRest().exchange(koneksiDB.URLORTHANC()+":"+koneksiDB.PORTORTHANC()+"/instances/"+list.asText()+"/preview", HttpMethod.GET, entity, byte[].class);
                 Files.write(Paths.get("./gambarradiologi/"+NoRawat+i+".jpg"),response.getBody());
                 i++;
            }
            tampilkanPesan("Pengambilan Gambar JPG dari Orthanc berhasil, silahkan lihat di dalam folder Aplikasi..!!");
        }catch(Exception e){
            lastError=e.getMessage()==null ? e.getClass().getSimpleName() : e.getMessage();
            root=mapper.createObjectNode();
            System.out.println("Orthanc AmbilJpg gagal : "+e.getClass().getSimpleName());
            tampilkanPesan("Gagal mengambil Gambar JPG dari Orthanc, silahkan hubungi administrator ..!!");
        }
        return root;
    }
    
    public JsonNode AmbilBmp(String NoRawat,String Series){
        lastError="";
        root=mapper.createObjectNode();
        try{
            headers = new HttpHeaders();
            headers.add("Authorization", "Basic "+authEncrypt);
            requestEntity = new HttpEntity(headers);
            requestJson=getRest().exchange(koneksiDB.URLORTHANC()+":"+koneksiDB.PORTORTHANC()+"/series/"+Series, HttpMethod.GET, requestEntity, String.class).getBody();
            root = mapper.readTree(requestJson);
            i=1;
            for(JsonNode list:root.path("Instances")){
                 headers = new HttpHeaders();
                 headers.add("Authorization", "Basic "+authEncrypt);
                 headers.add("Accept","image/bmp");
                 headers.setAccept(Collections.singletonList(MediaType.APPLICATION_OCTET_STREAM));
                 headers.setAccept(Collections.singletonList(MediaType.IMAGE_JPEG));
                 HttpEntity<String> entity = new HttpEntity<>(headers);
                 ResponseEntity<byte[]> response = getRest().exchange(koneksiDB.URLORTHANC()+":"+koneksiDB.PORTORTHANC()+"/instances/"+list.asText()+"/preview", HttpMethod.GET, entity, byte[].class);
                 Files.write(Paths.get("./gambarradiologi/"+NoRawat+i+".bmp"),response.getBody());
                 i++;
            }
            tampilkanPesan("Pengambilan Gambar BMP dari Orthanc berhasil, silahkan lihat di dalam folder Aplikasi..!!");
        }catch(Exception e){
            lastError=e.getMessage()==null ? e.getClass().getSimpleName() : e.getMessage();
            root=mapper.createObjectNode();
            System.out.println("Orthanc AmbilBmp gagal : "+e.getClass().getSimpleName());
            tampilkanPesan("Gagal mengambil Gambar BMP dari Orthanc, silahkan hubungi administrator ..!!");
        }
        return root;
    }
    
    public JsonNode AmbilDcm(String NoRawat,String Series){
        lastError="";
        root=mapper.createObjectNode();
        try{
            headers = new HttpHeaders();
            headers.add("Authorization", "Basic "+authEncrypt);
            requestEntity = new HttpEntity(headers);
            requestJson=getRest().exchange(koneksiDB.URLORTHANC()+":"+koneksiDB.PORTORTHANC()+"/series/"+Series, HttpMethod.GET, requestEntity, String.class).getBody();
            root = mapper.readTree(requestJson);
            i=1;
            for(JsonNode list:root.path("Instances")){
                 headers = new HttpHeaders();
                 headers.add("Authorization", "Basic "+authEncrypt);
                 headers.setAccept(Collections.singletonList(MediaType.APPLICATION_OCTET_STREAM));
                 headers.setAccept(Collections.singletonList(MediaType.IMAGE_JPEG));
                 HttpEntity<String> entity = new HttpEntity<>(headers);
                 ResponseEntity<byte[]> response = getRest().exchange(koneksiDB.URLORTHANC()+":"+koneksiDB.PORTORTHANC()+"/instances/"+list.asText()+"/file", HttpMethod.GET, entity, byte[].class);
                 Files.write(Paths.get("./gambarradiologi/"+NoRawat+i+".dcm"),response.getBody());
                 i++;
            }
            tampilkanPesan("Pengambilan Gambar DCM dari Orthanc berhasil, silahkan lihat di dalam folder Aplikasi..!!");
        }catch(Exception e){
            lastError=e.getMessage()==null ? e.getClass().getSimpleName() : e.getMessage();
            root=mapper.createObjectNode();
            System.out.println("Orthanc AmbilDcm gagal : "+e.getClass().getSimpleName());
            tampilkanPesan("Gagal mengambil Gambar DCM dari Orthanc, silahkan hubungi administrator ..!!");
        }
        return root;
    }
    
    public synchronized RestTemplate getRest(){
        if(restTemplate==null){
            factory=new HttpComponentsClientHttpRequestFactory();
            org.apache.http.params.HttpConnectionParams.setConnectionTimeout(
                factory.getHttpClient().getParams(),5000
            );
            factory.setReadTimeout(20000);
            restTemplate=new RestTemplate(factory);
        }
        return restTemplate;
    }
}
