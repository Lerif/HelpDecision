package model.domain.servicos;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.domain.entidades.ChamadaMetodo;
import model.domain.enumeradores.Regex;

public class Leitor {
	public List<ChamadaMetodo> ler(String arquivo) throws IOException, ParseException {

		FileInputStream fstream = new FileInputStream(arquivo);
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String linha = null;
		String nome = null;
		String idElemento = null, idElemento2 = null;
		String tipoElemento = null, tipoElemento2 = null;
		Date date1 = null;
		Date date2 = null;
		Matcher matcher;
		List<ChamadaMetodo> lista = new ArrayList<ChamadaMetodo>();

		boolean vezComeco = false;
		boolean achouComplex = false;
		boolean achouSimples = false;
		boolean achouVazio = false;
		SimpleDateFormat sdf = new SimpleDateFormat(Regex.DATEFORMAT.valor());
		Pattern NomeFunc = Pattern.compile(Regex.NOMEFUNC.valor());
		Pattern Comeco = Pattern.compile(Regex.COMECO.valor());
		Pattern Final = Pattern.compile(Regex.FINAL.valor());
		Pattern UmArg = Pattern.compile(Regex.UMARG.valor());
		Pattern DoisArg = Pattern.compile(Regex.DOISARGS.valor());
		Pattern NumVirNum = Pattern.compile(Regex.NUMVIRNUM.valor());
		

		while ((linha = br.readLine()) != null) {

			// COMECO 2
			if (Comeco.matcher(linha).find() && DoisArg.matcher(linha).find() && !achouComplex && !achouSimples
					&& !achouVazio) {
				achouComplex = true;
				vezComeco = true;
				String[] partes = linha.split(Regex.COMECO.valor());
				String dateInString = partes[0];
				date1 = sdf.parse(dateInString);
				matcher = NomeFunc.matcher(partes[1]);
				if (matcher.find()) {
					nome = matcher.group(0);
				}
				matcher = DoisArg.matcher(linha);
				if (matcher.find()) {
					String[] argus = matcher.group(0).split(Regex.FECHALETRAABRE.valor());
					String[] primeiro = argus[0].split(",");
					String[] segundo = argus[1].split(",");
					idElemento = primeiro[0];
					idElemento2 = segundo[0];
					tipoElemento = primeiro[1];
					tipoElemento2 = segundo[1];
				}
			}

			// COMECO 1
			if (Comeco.matcher(linha).find() && UmArg.matcher(linha).find() && !achouComplex && !achouSimples
					&& !achouVazio) {
				achouSimples = true;
				vezComeco = true;
				String[] partes = linha.split(Regex.COMECO.valor());
				String dateInString = partes[0];
				date1 = sdf.parse(dateInString);
				matcher = NomeFunc.matcher(partes[1]);
				if (matcher.find()) {
					nome = matcher.group(0);
				}
				String[] nd = partes[1].split(" ");
				for (String s : nd) {
					matcher = NumVirNum.matcher(s);
					if (matcher.find()) {
						idElemento = matcher.group(0);
					}
				}
				String[] ids = idElemento.split(",");
				idElemento = ids[0];
				tipoElemento = ids[1];
			}

			// COMECO 0
			if (Comeco.matcher(linha).find() && !achouComplex && !achouSimples) {
				achouVazio = true;
				vezComeco = true;
				String[] partes = linha.split(Regex.COMECO.valor());
				String dateInString = partes[0];
				date1 = sdf.parse(dateInString);
				matcher = NomeFunc.matcher(partes[1]);
				if (matcher.find()) {
					nome = matcher.group(0);
				}
			}

			// FIM 0
			if (Final.matcher(linha).find() && !vezComeco && achouVazio) {
				String[] partes = linha.split(Regex.FINAL.valor());
				String dateInString = partes[0];
				date2 = sdf.parse(dateInString);
				long date3 = date2.getTime() - date1.getTime();
				if (date3 < 0)
					date3 += 1000;
				lista.add(ChamadaMetodo.nova(1, nome, date1, date2, null, null, date3));
				achouVazio = false;
				nome = "";
			}

			// FIM 1
			if (Final.matcher(linha).find() && !vezComeco && achouSimples) {
				String[] partes = linha.split(Regex.FINAL.valor());
				String dateInString = partes[0];
				date2 = sdf.parse(dateInString);
				long date3 = date2.getTime() - date1.getTime();
				if (date3 < 0)
					date3 += 1000;
				lista.add(ChamadaMetodo.nova(1, nome, date1, date2, idElemento, tipoElemento, date3));
				achouSimples = false;
				nome = "";
			}

			// FIM 2
			if (Final.matcher(linha).find() && !vezComeco && achouComplex) {
				String[] partes = linha.split(Regex.FINAL.valor());
				String dateInString = partes[0];
				date2 = sdf.parse(dateInString);
				long date3 = date2.getTime() - date1.getTime();
				if (date3 < 0)
					date3 += 1000;
				lista.add(ChamadaMetodo.nova(1, nome, date1, date2, idElemento, tipoElemento, date3));
				lista.add(ChamadaMetodo.nova(1, nome, date1, date2, idElemento2, tipoElemento2, date3));
				achouComplex = false;
				nome = "";
			}
			vezComeco = false;
		}
		br.close();
		return lista;
	}

}