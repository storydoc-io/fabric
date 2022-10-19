package io.storydoc.fabric.jdbc;

import io.storydoc.fabric.console.app.navigation.NavItem;
import io.storydoc.fabric.console.app.navigation.NavigationRequest;
import io.storydoc.fabric.jdbc.metadata.DBMetaData;
import junit.framework.TestCase;

import java.util.List;

public class NavigationCalculatorTest extends TestCase {

    public void test_default_request() {

        // given metadata of the sample db
        DBMetaData dbMetaData = new SampleDBTestUtil().getSalesDBMataData();
        NavigationCalculator navigationCalculator = new NavigationCalculator(dbMetaData);

        // when I request the default navigation items
        NavigationRequest navigationRequest = NavigationRequest.builder()
                .build();
        List<NavItem> navItemList = navigationCalculator.getNavigation(navigationRequest);

        // then I get the list of tables
        assertNotNull(navItemList);
        navItemList.forEach(navItem ->
            assertEquals(JDBCConstants.NAV_TYPE__SELECT_ALL, navItem.getType())
        );

    }

    public void test_follow_fk_when_selecting_a_row() {

        // given metadata of the sample db
        DBMetaData dbMetaData = new SampleDBTestUtil().getSalesDBMataData();
        NavigationCalculator navigationCalculator = new NavigationCalculator(dbMetaData);

        // when I request the navigation for a selected row in ORDERS (table with outgoing fk relations to CUSTOMERS and PRODUCTS)
        NavigationRequest navigationRequest = NavigationRequest.builder()
                .navItem(NavItem.builder()
                    .type(JDBCConstants.NAV_TYPE__SELECT_ALL)
                    .id(SampleDBTestUtil.TABLE_ORDERS)
                    .build())
                .build();

        List<NavItem> navItemList = navigationCalculator.getNavigation(navigationRequest);

        // then I get the list of fk relations I can follow from this record
        assertNotNull(navItemList);
        assertEquals(2, navItemList.size());
        navItemList.forEach(navItem -> {
            assertEquals(JDBCConstants.NAV_TYPE__FOLLOW_FK, navItem.getType());
        });

    }

    public void test_follow_reverse_fk_when_selecting_a_row() {

        // given metadata of the sample db
        DBMetaData dbMetaData = new SampleDBTestUtil().getSalesDBMataData();
        NavigationCalculator navigationCalculator = new NavigationCalculator(dbMetaData);

        // when I request the navigation for a CUSTOMERS (which has a fk from ORDERS)
        NavigationRequest navigationRequest = NavigationRequest.builder()
                .navItem(NavItem.builder()
                        .type(JDBCConstants.NAV_TYPE__SELECT_ALL)
                        .id(SampleDBTestUtil.TABLE_CUSTOMERS)
                        .build())
                .build();

        List<NavItem> navItemList = navigationCalculator.getNavigation(navigationRequest);

        // then I get the list of fk relations pointing to this record, i.e. the orders for the customer
        assertNotNull(navItemList);
        assertEquals(1, navItemList.size());
        navItemList.forEach(navItem -> {
            assertEquals(JDBCConstants.NAV_TYPE__FOLLOW_REVERSE_FK, navItem.getType());
            assertEquals(SampleDBTestUtil.TABLE_ORDERS, navItem.getLabel());
        });
    }

}