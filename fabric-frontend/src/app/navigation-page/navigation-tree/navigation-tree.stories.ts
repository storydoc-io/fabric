import {NavigationTreeComponent, NavTreeItemDto} from "./navigation-tree.component";
import {moduleMetadata} from "@storybook/angular";
import {Meta, Story} from "@storybook/angular/types-6-0";
import {NavigationTreeItemComponent} from "../navigation-tree-item/navigation-tree-item.component";
import {FontAwesomeModule} from "@fortawesome/angular-fontawesome";

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

let rootItems = [
    {
        label: 'ORDERS'
    },
    {
        label: 'PRODUCTS'
    },
    {
        label: 'STORES'
    }
]

let rootNode: NavTreeItemDto = {
    root: true,
    navItems : rootItems,

}

let jobsNode: NavTreeItemDto = {
    root: false,
    label: "ORDERS",
    columns: listOfValues(5, "column_"),
    rows: [
        { values : listOfValues(5, "row_1_") },
        { values : listOfValues(5, "row_2_") },
        { values : listOfValues(5, "row_3_") },
        { values : listOfValues(5, "row_4_") },
    ],
    navItems: [],
    children: []

}

export const RootNode = NavigationTreeComponentStory.bind({});
RootNode.args = {
    tree: rootNode
}

export const SelectJobs =  NavigationTreeComponentStory.bind({});
SelectJobs.args = {
    tree: jobsNode
}

export const WithChildren = NavigationTreeComponentStory.bind({});
WithChildren.args = {
    tree: <NavTreeItemDto> {
        root: false,
        label: "ORDERS",
        columns: listOfValues(5, "column_"),
        rows: [
            { values : listOfValues(5, "row_1_") },
            { values : listOfValues(5, "row_2_") },
            { values : listOfValues(5, "row_3_") },
            { values : listOfValues(5, "row_4_") },
        ],
        children: [
            <NavTreeItemDto>{
                root: false,
                label: "CUSTOMERS",
                columns: listOfValues(5, "column_"),
                rows: [
                    { values : listOfValues(5, "row_1_") },
                ],
            },
            <NavTreeItemDto>{
                root: false,
                label: "PRODUCTS",
                columns: listOfValues(7, "column_"),
                rows: [
                    { values : listOfValues(7, "row_1_") },
                ],
            },
        ]
    }
}