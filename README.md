# Restaurant Recommendation Engine

A recommendation engine for a food delivery app that considers the following criteria to sort and display the top 100 unique restaurants:

    Cuisine of the restaurant: North Indian, Chinese, South Indian, etc.
    Cost bracket: 1, 2, 3, 4, 5 (increasing order from cheap to costly)
    Featured restaurants: Restaurants officially tested and recommended by the app.
    New restaurants: Restaurants onboarded in the last 48 hours.
    Rating: Average user rating for the restaurant (from 0.0 - 5.0)

The algorithm takes into account users' order history, considering their primary and secondary preferences for cuisine and cost bracket. The engine sorts the available restaurants according to the following conditions:

    1. Featured restaurants of primary cuisine and primary cost bracket. If none, then all featured restaurants of primary cuisine, secondary cost, and secondary cuisine, primary cost.
    2. All restaurants of primary cuisine, primary cost bracket with rating >= 4.
    3. All restaurants of primary cuisine, secondary cost bracket with rating >= 4.5.
    4. All restaurants of secondary cuisine, primary cost bracket with rating >= 4.5.
    5. Top 4 newly created restaurants by rating.
    6. All restaurants of primary cuisine, primary cost bracket with rating < 4.
    7. All restaurants of primary cuisine, secondary cost bracket with rating < 4.5.
    8. All restaurants of secondary cuisine, primary cost bracket with rating < 4.5.
    9. All restaurants of any cuisine, any cost bracket.

The engine returns an array of restaurant IDs in the right sorting order
