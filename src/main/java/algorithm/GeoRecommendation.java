package algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import db.DBConnection;
import db.DBConnectionFactory;
import entity.Item;

public class GeoRecommendation {

	public List<Item> recommendItems(String userId, double lat, double lon) {
	
		List<Item> recommendedItems = new ArrayList<>();
		DBConnection conn = DBConnectionFactory.getConnection();
		
		//Get all favorite items
		Set<String> favoriteItemsIds = conn.getFavoriteItemIds(userId);
		
		//Get all categories of favorited items, sort by count
		Map<String, Integer> allCategories = new HashMap<>();
		for (String itemId : favoriteItemsIds) {
			Set<String> categories = conn.getCategories(itemId);
			for (String category : categories) {
				allCategories.put(category, allCategories.getOrDefault(category, 0) + 1);
			}
		}
		
		List<Entry<String, Integer>> categoryList = new ArrayList<>(allCategories.entrySet());
		Collections.sort(categoryList, (a, b) -> b.getValue() - a.getValue());
		
		// Search based on category, filter out favorite events, sort by distanec
		Set<Item> visitedItems = new HashSet<>();
		for (Entry<String, Integer> category : categoryList) {
			List<Item> items = conn.searchItems(lat, lon, category.getKey());
			List<Item> filteredItems = new ArrayList<>();
			for (Item item : items) {
				if (!favoriteItemsIds.contains(item.getItemId()) && !visitedItems.contains(item)) {
					filteredItems.add(item);
				}
			}
			Collections.sort(filteredItems, (a, b) -> Double.compare(a.getDistance(), b.getDistance()));
			
			visitedItems.addAll(items);
			
			recommendedItems.addAll(filteredItems);
		}
		
		
		return recommendedItems;
	}
}
