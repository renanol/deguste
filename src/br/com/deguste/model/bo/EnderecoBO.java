package br.com.deguste.model.bo;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import sun.misc.BASE64Encoder;

import br.com.deguste.model.pojos.EnderecoResponse;

import com.google.gson.Gson;

public class EnderecoBO implements Serializable {

	private static final long serialVersionUID = 7493380156955298156L;

	public EnderecoResponse obterEnderecoPorCep(String cep) throws Exception{
		try{
			if(cep != null && !cep.isEmpty()){
				Gson gson = new Gson();
				EnderecoResponse resut = gson.fromJson(jsonCoord(URLEncoder.encode(cep, "UTF-8")), EnderecoResponse.class);
				if(resut != null)
					return resut;
			}

			return new EnderecoResponse();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Erro ao buscar endereço.");
		}
	}

	private String jsonCoord(String cep) throws IOException {
		System.setProperty("proxySet", "true");
		System.setProperty("http.proxyHost","10.96.0.26");
		System.setProperty("http.proxyPort", "3128");
		System.setProperty("https.proxyHost","10.96.0.26");
		System.setProperty("https.proxyPort", "3128");
		
		BASE64Encoder encoder = new BASE64Encoder();
	    URL url = new URL("http://correiosapi.apphb.com/cep/" + cep);
	    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	    connection.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
	    connection.setRequestProperty("Proxy-Authorization", "Basic " + encoder.encode(new String("usersgp:sgpproxy").getBytes()));
	    connection.setRequestProperty("Authorization", "Token token=c1b4aa395b9f1120d1fecdc95a21506d");
	    connection.connect();

	    boolean isError = connection.getResponseCode() >= 400;
	    InputStream input = isError ? connection.getErrorStream() : connection.getInputStream();

	    BufferedReader in = new BufferedReader(new InputStreamReader((input), "UTF-8"));
	    String inputLine;
	    String jsonResult = "";

	    while ((inputLine = in.readLine()) != null)
	        jsonResult += inputLine;

	    in.close();
	    return jsonResult;
	}
}