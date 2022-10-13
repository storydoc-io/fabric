import {Meta, Story} from '@storybook/angular/types-6-0';
import {moduleMetadata} from "@storybook/angular";
import {StatusComponent} from "./status.component";

export default {
    title: 'Fabric/Common/Styleguide/Status',
    component: StatusComponent,
    argTypes: {
    },
    decorators: [
        moduleMetadata({
            declarations: [
                StatusComponent
            ],
            imports: [
            ]
        }),
    ],

} as Meta;

const StatusComponentStory: Story<StatusComponent> = (args: StatusComponent) => ({
    component: StatusComponent,
    props: args,

})

export const StatusOK = StatusComponentStory.bind({});
StatusOK.args = {
    status : 'OK'
}

export const ConnectionProblem = StatusComponentStory.bind({});
ConnectionProblem.args = {
    status : 'Connection problem'
}
