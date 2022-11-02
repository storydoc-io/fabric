import {Meta, Story} from '@storybook/angular/types-6-0';
import {moduleMetadata} from "@storybook/angular";
import {ConfirmationDialogComponent, ConfirmationDialogSpec} from "./confirmation-dialog.component";
import {FontAwesomeModule} from "@fortawesome/angular-fontawesome";

export default {
    title: 'Fabric/Common/ConfirmationDialog',
    component: ConfirmationDialogComponent,
    argTypes: {},
    decorators: [
        moduleMetadata({
            declarations: [
                ConfirmationDialogComponent
            ],
            imports: [
                FontAwesomeModule,
            ]
        }),
    ],

} as Meta;

const NavComponentStory: Story<ConfirmationDialogComponent> = (args: ConfirmationDialogComponent) => ({
    component: ConfirmationDialogComponent,
    props: args,

})

export const Default = NavComponentStory.bind({});
Default.args = {
    spec: <ConfirmationDialogSpec> {
        title: 'Confirm Lorem   ',
        message: `Lorem ipsum ?`,
        warning: `Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatu.'`,
        confirm: () => {
        },
        cancel: () => {
        }

    }
}