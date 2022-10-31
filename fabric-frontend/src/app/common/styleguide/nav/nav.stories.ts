import {Meta, Story} from '@storybook/angular/types-6-0';
import {moduleMetadata} from "@storybook/angular";
import {NavComponent, NavItemSpec, NavSpec} from "./nav.component";
import {FontAwesomeModule} from "@fortawesome/angular-fontawesome";

export default {
    title: 'Fabric/Common/Styleguide/Nav',
    component: NavComponent,
    argTypes: {},
    decorators: [
        moduleMetadata({
            declarations: [
                NavComponent
            ],
            imports: [
                FontAwesomeModule,
            ]
        }),
    ],

} as Meta;

const NavComponentStory: Story<NavComponent> = (args: NavComponent) => ({
    component: NavComponent,
    props: args,

})

export const Default = NavComponentStory.bind({});
Default.args = {
    spec: <NavSpec>{
        defaultSelection: 'KEY1',
        items: [
            <NavItemSpec>{
                label: 'item 1',
                key: 'KEY1'
            },
            <NavItemSpec>{
                label: 'item 2',
                key: 'KEY2'
            },
            <NavItemSpec>{
                label: 'item 3',
                key: 'KEY3'
            },
        ],
        select: (key) => console.log('selected: ' + key)
    }
}