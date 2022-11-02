import {Meta, Story} from '@storybook/angular/types-6-0';
import {moduleMetadata} from "@storybook/angular";
import {IntroComponent} from "./intro.component";
import {FontAwesomeModule} from "@fortawesome/angular-fontawesome";

export default {
    title: 'Fabric/Common/Styleguide/Intro',
    component: IntroComponent,
    argTypes: {},
    decorators: [
        moduleMetadata({
            declarations: [
                IntroComponent
            ],
            imports: [
                FontAwesomeModule,
            ]
        }),
    ],

} as Meta;

const IntroComponentStory: Story<IntroComponent> = (args: IntroComponent) => ({
    component: IntroComponent,
    props: args,

})

export const Default = IntroComponentStory.bind({});
Default.args = {
    title: 'This is the title'
}