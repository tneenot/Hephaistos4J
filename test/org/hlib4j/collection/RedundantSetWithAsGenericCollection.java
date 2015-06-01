package org.hlib4j.collection;

/**
 * Unit tests for the {@link RedundantSet} include into the {@link CollectionTTemplateTest} to control the Liskov principle.
 */
public class RedundantSetWithAsGenericCollection extends CollectionTTemplateTest<java.util.Collection<Integer>> {

    @Override
    protected void instanciateReferencesTestData() {
        this.collectionOfThisTemplate = new RedundantSet<>();
        this.invalidCollectionValues = new RedundantSet<>();
        this.testingCollection = new RedundantSet<>();
    }

    @Override
    public void test_Add_InvalidData() {
        // Not available for this collection type. Skip test.
    }

    @Override
    public void test_AddAll_InvalidData() {
        // Not available for this collection type. Skip test.
    }

    @Override
    public void test_Add_InvalidData_ControlIsNotInCollection() {
        // Not available for this collection type. Skip test.
    }
}
