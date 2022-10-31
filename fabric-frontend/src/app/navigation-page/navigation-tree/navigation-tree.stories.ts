import {NavigationTreeComponent, NavTreeItemDto} from "./navigation-tree.component";
import {moduleMetadata} from "@storybook/angular";
import {Meta, Story} from "@storybook/angular/types-6-0";
import {NavigationTreeItemComponent} from "../navigation-tree-item/navigation-tree-item.component";
import {FontAwesomeModule} from "@fortawesome/angular-fontawesome";
import {FormsModule} from "@angular/forms";

export default {
    title: 'Fabric/Navigation/NavigationTree',
    component: NavigationTreeComponent,
    argTypes: {
    },
    decorators: [
        moduleMetadata({
            declarations: [
                NavigationTreeComponent,
                NavigationTreeItemComponent
            ],
            imports: [
                FormsModule,
                FontAwesomeModule,
            ]
        }),
    ],

} as Meta;

const NavigationTreeComponentStory: Story<NavigationTreeComponent> = (args: NavigationTreeComponent) => ({
    component: NavigationTreeComponent,
    props: args,
})

function listOfValues(count: number, prefix: string): string[]{
    return [...Array(count).keys()].map(i => prefix + i)
}

let rootNode: NavTreeItemDto = {
    root: true,
    navItems : [
        {
            label: "-- select table --"
        },
        {
            label: 'BRANCHES'
        },
        {
            label: 'CUSTOMERS'
        },
        {
            label: 'ORDERS'
        },
        {
            label: 'ORDER_ITEMS'
        },
        {
            label: 'PRODUCTS'
        },
        {
            label: 'STOCK'
        },
        {
            label: 'STORES'
        },
        {
            label: 'WAREHOUSES'
        },
    ]
}

let ordersNode: NavTreeItemDto = {
    root: false,
    label: "ORDERS",
    columns: listOfValues(5, "column_"),
    rows: [
        { values : listOfValues(5, "row_1_") },
        { values : listOfValues(5, "row_2_") },
        { values : listOfValues(5, "row_3_") },
        { values : listOfValues(5, "row_4_") },
    ],
    navItems : [
        {
            label: "-- select table --"
        },
        {
            label: 'PRODUCTS'
        },
        {
            label: 'CUSTOMERS'
        },
        {
            label: 'ORDER_ITEMS'
        },
    ],
    children: []

}

let customersNode = <NavTreeItemDto>{
    root: false,
    label: "CUSTOMERS",
    columns: listOfValues(5, "column_"),
    rows: [
        { values : listOfValues(5, "row_1_") },
    ],
};

let productsNode = <NavTreeItemDto>{
    root: false,
    label: "PRODUCTS",
    columns: listOfValues(7, "column_"),
    rows: [
        { values : listOfValues(7, "row_1_") },
    ],
};



export const RootNode = NavigationTreeComponentStory.bind({});
RootNode.args = {
    tree: rootNode
}

export const SelectOrders =  NavigationTreeComponentStory.bind({});
SelectOrders.args = {
    tree: {
        ... rootNode,
        children: [
            ordersNode
        ]
    }
}

export const WithChildren = NavigationTreeComponentStory.bind({});

WithChildren.args = {
    tree: {
        ... rootNode,
        children: [
            {
                ... ordersNode,
                children: [
                    customersNode,
                    productsNode,
                ]
            }
        ]
    }

}