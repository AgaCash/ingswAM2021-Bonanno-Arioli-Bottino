package model.warehouse;

import exceptions.FullWarehouseException;
import exceptions.UnusableCardException;
import model.cards.ExtraDepot;
import model.resources.Resource;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WarehouseDepotTest {
    WarehouseDepot w = new WarehouseDepot();
    //todo fare una  batteria di test seri

    @Test
    void addNewExtraDepot() {

        //some initializing
        w = new WarehouseDepot();
        ArrayList<Resource> addedResources = new ArrayList<>();
        ArrayList<Resource> resources = new ArrayList<>();
        resources.add(Resource.SERVANT); resources.add(Resource.SERVANT); //SE SE
        ExtraDepot card1 = new ExtraDepot(0, true, null, resources);
        //add card to warehouse
        try {
            w.addNewExtraDepot(card1);
        }catch(UnusableCardException e){
            System.out.println(e.getMessage());
        }
        //test if card1 is working
        try {
            addedResources.add(Resource.SERVANT);
            w.addResource(Resource.SERVANT);
            System.out.println(w.convert().toString());
            //System.out.println(w.status());
            addedResources.add(Resource.SERVANT);
            w.addResource(Resource.SERVANT);
            System.out.println(w.convert().toString());
            //System.out.println(w.status());
            addedResources.add(Resource.SERVANT);
            w.addResource(Resource.SERVANT);
            System.out.println(w.convert().toString());
            addedResources.add(Resource.SERVANT);
            w.addResource(Resource.SERVANT);
            System.out.println(w.convert().toString());
            addedResources.add(Resource.SERVANT);
            w.addResource(Resource.SERVANT);
            System.out.println(w.convert().toString());
        }catch (FullWarehouseException e){
            System.out.println(e.getMessage());
            assertTrue(false);
        }
        assertTrue(w.isPresent(addedResources));
        //test if not adds more than 5 items
        try {
            addedResources.add(Resource.SERVANT);
            w.addResource(Resource.SERVANT);
        }catch(FullWarehouseException e){
            System.out.println(e.getMessage());
            assertTrue(true);
        }
        //some initializing
        ArrayList<Resource> resources2 = new ArrayList<>();
        resources2.add(Resource.COIN); resources2.add(Resource.COIN);
        ExtraDepot card2 = new ExtraDepot(0, true, null, resources2);
        try {
            w.addNewExtraDepot(card2);
        }
        catch(UnusableCardException e){
            System.out.println(e.getMessage());
            assertTrue(false);
        }
        addedResources = new ArrayList<>();
        //test if cards are working
        try {

            addedResources.add(Resource.COIN);
            w.addResource(Resource.COIN);
            System.out.println(w.convert().toString());
            System.out.println(w.status());
            addedResources.add(Resource.COIN);
            w.addResource(Resource.COIN);
            System.out.println(w.convert().toString());
            System.out.println(w.status());
            addedResources.add(Resource.COIN);
            w.addResource(Resource.COIN);
            System.out.println(w.convert().toString());
            System.out.println(w.status());
            addedResources.add(Resource.COIN);
            w.addResource(Resource.COIN);
            System.out.println(w.convert().toString());
            System.out.println(w.status());
        }catch(FullWarehouseException e){
            System.out.println(e.getMessage());
            assertTrue(false);
        }
        assertTrue(w.isPresent(addedResources));
        //here w will broke
        try{
            w.addResource(Resource.COIN);
            assertTrue(false);
        }catch(FullWarehouseException e){
            System.out.println(e.getMessage());
            assertTrue(true);
        }



    }

    @Test
    void addResource() {
        ArrayList<Resource> addedResources = new ArrayList<>();
        w = new WarehouseDepot();
        //test adding 1 resource of the same type
        try {
            w.addResource(Resource.COIN);
            addedResources.add(Resource.COIN);
        }
        catch(FullWarehouseException e){
            System.out.println(e.getMessage());
        }
        assertTrue(w.isPresent(addedResources));

        //test adding 3 model.resources of the same type
        try {
            w.addResource(Resource.COIN);
            addedResources.add(Resource.COIN);
        }catch(FullWarehouseException e){ System.out.println(e.getMessage()); }
        try {
            w.addResource(Resource.COIN);
            addedResources.add(Resource.COIN);
        }catch(FullWarehouseException e) {
            System.out.println(e.getMessage());
        }
        assertTrue(w.isPresent(addedResources));


        //test adding 4 model.resources of the same type
        try {
            addedResources.add(Resource.COIN);
            w.addResource(Resource.COIN);

        }catch(FullWarehouseException e ) {
            System.out.println(e.getMessage());
        }
        //System.out.println("added: "+ addedResources);
        assertFalse(w.isPresent(addedResources));
        //test adding 2 another model.resources


        addedResources.remove(Resource.COIN);
        try {
            addedResources.add(Resource.SERVANT);
            w.addResource(Resource.SERVANT);
        }catch(FullWarehouseException e){
            System.out.println(e.getMessage());
        }
        try {
            addedResources.add(Resource.SERVANT);
            w.addResource(Resource.SERVANT);

        }catch(FullWarehouseException e){
            System.out.println(e.getMessage());
        }
        assertTrue(w.isPresent(addedResources));

        //test adding 1 another resource
        try {
            addedResources.add(Resource.SHIELD);
            w.addResource(Resource.SHIELD);
        }catch(FullWarehouseException e){
            System.out.println(e.getMessage());
        }
        assertTrue(w.isPresent(addedResources));

        //now model.warehouse is full... test adding something
        try {
            addedResources.add(Resource.SHIELD);
            w.addResource(Resource.SHIELD);
        }catch (FullWarehouseException e){
            System.out.println(e.getMessage());
            System.out.println("riga 142");
        }
        assertFalse(w.isPresent(addedResources));
        try {
            addedResources.add(Resource.SERVANT);
            w.addResource(Resource.SERVANT);

        } catch(FullWarehouseException e){
            System.out.println(e.getMessage());
            System.out.println("riga 151");
        }
        assertFalse(w.isPresent(addedResources));

        addedResources.remove(Resource.SHIELD);
        addedResources.remove(Resource.SERVANT);
        System.out.println(addedResources);
        System.out.println(w.status());

        //test error 2-2-2
        w = new WarehouseDepot();
        addedResources.clear();
        try {
            addedResources.add(Resource.SERVANT);
            w.addResource(Resource.SERVANT);
            addedResources.add(Resource.SERVANT);
            w.addResource(Resource.SERVANT);
            addedResources.add(Resource.SHIELD);
            w.addResource(Resource.SHIELD);
            addedResources.add(Resource.SHIELD);
            w.addResource(Resource.SHIELD);
            addedResources.add(Resource.COIN);
            w.addResource(Resource.COIN);
        } catch(FullWarehouseException e){
            System.out.println(e.getMessage());
            System.out.println("riga 176");
        }
        assertTrue(w.isPresent(addedResources));

        try{
            addedResources.add(Resource.COIN);
            w.addResource(Resource.COIN);
        }catch(FullWarehouseException e){
            System.out.println(e.getMessage());
            System.out.println("riga 186");
        }
        System.out.println(w.status());
        assertFalse(w.isPresent(addedResources));



    }

    @Test
    void removeResource() {
        /*
        ArrayList<Resource> removedResources = new ArrayList<>();
        //there's only one instance of this
        w.addResource(Resource.SHIELD);
        w.removeResource(Resource.SHIELD); removedResources.add(Resource.SHIELD);
        assertFalse(w.isPresent(removedResources));
        //there are two servants
        //test that delete one don't delete both
        removedResources.remove(Resource.SHIELD);
        w.addResource(Resource.SERVANT); w.addResource(Resource.SERVANT);
        w.removeResource(Resource.SERVANT); removedResources.add(Resource.SERVANT);
        assertTrue(w.isPresent(removedResources));

         */
    }

}