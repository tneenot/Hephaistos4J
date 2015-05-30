package org.hlib4j.collection;

/**
 * Created by beonnet on 29/05/15.
 */
public class RedundantSetWithAsGenericCollection extends CollectionTTemplateTest<java.util.Collection<Integer>> {

    @Override
    protected void instanciateReferencesTestData() {
        this.sourceListRef = new RedundantSet<>();
        this.invalidListRef = new RedundantSet<>();
        this.collectionListRef = new RedundantSet<>();
    }

    @Override
    public void test_Add_InvalidData() {
        // Not available for this collection type. Test skip.
    }

    @Override
    public void test_AddAll_InvalidData() {
        // Not available for this collection type. Test skip.
    }

    @Override
    public void test_Add_InvalidData_ControlIsNotInCollection() {
        // Not available for this collection type. Test skip.
    }
}
