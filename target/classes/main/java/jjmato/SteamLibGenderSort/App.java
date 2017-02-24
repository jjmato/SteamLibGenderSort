package jjmato.SteamLibGenderSort;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mashape.unirest.http.exceptions.UnirestException;
import jjmato.SteamLibGenderSort.SteamApi.SteamApiService;
import jjmato.SteamLibGenderSort.SteamApi.pojos.Game;

/**
 * Hello world!
 *
 */
public class App {


	public static void main(String[] args) {

		Scanner scanner =new Scanner(System.in);
		SteamApiService api = new SteamApiService();
		Ohs ohs = new Ohs();

		System.out.println("please! enter steam nickname: ");
		String steamNickName = scanner.nextLine();

		List<Game> masculineGames = new ArrayList();
		List<Game> femaleGames = new ArrayList();
		List<Game> chooseGames = new ArrayList();
		List<Game> otherGames = new ArrayList();

		try {
			String steamUserID = api.getSteamId(steamNickName);
			Map<String, Game> gamesOfLibrary = api.getGamesOfLibrary(steamUserID);
			gamesOfLibrary.values().forEach((Game i)-> {
				String s = "o";
				if (i.getGenderSelectType()!=null && !i.getGenderSelectType().isEmpty()){
					System.out.println( i.getName() + " error, cplesae! enter gender select type: (m: masculine, f: female, c: choose, o: other)");
					String v = scanner.nextLine();
					i.setGenderSelectType(v);
					//use first char because the user can be stupid
					if (i.getGenderSelectType().startsWith("c")){
						chooseGames.add(i);
					}
					else if (i.getGenderSelectType().startsWith("o")){
						otherGames.add(i);
					}
					else if (i.getGenderSelectType().startsWith("f")){
						femaleGames.add(i);
					}
					else if (i.getGenderSelectType().startsWith("m")){
						masculineGames.add(i);
					}
				}
				i.setGenderSelectType(s);
			});
			api.updateCacheFile(gamesOfLibrary);

			ohs.doSomething(gamesOfLibrary.size(), masculineGames.size(), femaleGames.size(), chooseGames.size(), otherGames.size());

		} catch (UnirestException e) {
			e.printStackTrace();
		}


	}
}