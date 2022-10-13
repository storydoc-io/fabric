import {Meta, Story} from '@storybook/angular/types-6-0';
import {moduleMetadata} from "@storybook/angular";
import {TableComponent} from "./table.component";
import {Row} from "@fabric/models";

export default {
    title: 'Fabric/Console/Table',
    component: TableComponent,
    argTypes: {
    },
    decorators: [
        moduleMetadata({
            declarations: [
                TableComponent
            ],
            imports: [
            ]
        }),
    ],

} as Meta;

const TableComponentStory: Story<TableComponent> = (args: TableComponent) => ({
    component: TableComponent,
    props: args,
})

export const Result = TableComponentStory.bind({});
Result.args = {
    columns: [
        {
            "name": "cust_id"
        },
        {
            "name": "name"
        }
    ],
    rows :  <Row[]> [
        {
            "values": [
                "4",
                "Smith"
            ]
        },
        {
            "values": [
                "5",
                "Jones"
            ]
        }
    ]
}
