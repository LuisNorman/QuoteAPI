package com.example.hw3;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.*;

class QuoteObject {
    static int id;
    static String quote;

    public QuoteObject(int id, String quote) {
        this.id = id;
        this.quote = quote;
    }
}

// Class to hold the current id of string
class QuoteID {
    static int id = 6;

    // Make it a singleton pattern
    private QuoteID() {
    }

    public static int getID() {
        int val = id; // get id
        incrementID(); // increment id
        return val; // return old/correct id
    }

    // increment id
    public static void incrementID() {
        ++id;
    }
}
// QUOTE SERVICE CLASS
@Path("/quotes")
@Consumes("text/plain")
@Produces(MediaType.APPLICATION_JSON)
public class QuoteService {

    static List<QuoteObject> quotes = new LinkedList<>(); // global array of quotes
    static HashMap<Integer, QuoteObject> quoteMap = new HashMap<>(); // map with id as key and quote as val


    // Create first five quote objects

    static QuoteObject firstQuote = new QuoteObject(1, "Work hard - Play hard");
    static QuoteObject secondQuote = new QuoteObject(2, "Never be the smartest in the room.");
    static QuoteObject thirdQuote = new QuoteObject(3, "Never give up.");
    static QuoteObject fourthQuote = new QuoteObject(4, "Sleep is amazing.");
    static QuoteObject fifthQuote = new QuoteObject(5, "Only the strong survive");


    // Add all initial objects to list and map
    static {
        quotes.add(firstQuote);
        quotes.add(secondQuote);
        quotes.add(thirdQuote);
        quotes.add(fourthQuote);
        quotes.add(fifthQuote);

        quoteMap.put(firstQuote.id, firstQuote);
        quoteMap.put(secondQuote.id, secondQuote);
        quoteMap.put(thirdQuote.id, thirdQuote);
        quoteMap.put(fourthQuote.id, fourthQuote);
        quoteMap.put(fifthQuote.id, fifthQuote);
    }



    // Gets all quotes that have been added
    // to the service ordered by identifier
    @GET
    @Path("/getAllQuotes") // quotes/getAllQuotes
    public List<QuoteObject> getAllQuotes() {
        return quotes;
    }

    // Get a single quote by its identifier
    @GET
    @Path("/getQuoteByID/{id}") // quotes/getQuoteByID/1
    public QuoteObject getQuoteById(@PathParam("id") int id) {
        return quoteMap.get(id);
    }

    // Adds a quote and return the quote identifier
    @POST
    @Path("addQuote/{quote}") // quotes/addQuote?="my new quote"
    public int addQuote(@QueryParam("quote") String quote) {
        int id = QuoteID.getID(); // Get next id of new quote

        QuoteObject newQuoteObject = new QuoteObject(id, quote); // create new quote

        quotes.add(newQuoteObject); // add new quote object to list

        return id; // return id of new quote;
    }

    // Replace a quote with a given identifier with a new quote
    @GET
    @POST
    @Path("/replaceQuote/id/{id}/newQuote/{newQuote}/")  // quotes/replaceQuote/id?=1/newQuote?="The new quote"
    public QuoteObject updateQuote(@QueryParam("id") int id, @QueryParam("newQuote") QuoteObject newQuote) {
        quoteMap.put(id, newQuote); // change quote in map

        // Loop and find quote to change in list and change it
        for (QuoteObject curQuote : quotes) {
            if (curQuote.id == id) { // Found quote to change
                curQuote.quote = newQuote.quote; // update new quote in list
            }
        }
        return newQuote;
    }

    // Delete the quote with the given identifier
    @DELETE
    @Path("/delete/{id}") // quotes/delete/2
    public void deleteQuote(@PathParam("id") int id) {
        // Check if quote exists
        if (quoteMap.get(id) != null) {

            quoteMap.remove(id); // remove quote from map

            // Loop and find quote to delete in list
            for (QuoteObject curQuote : quotes) {
                if (curQuote.id == id) {
                    quotes.remove(curQuote);
                }
            }
        }

    }
}


