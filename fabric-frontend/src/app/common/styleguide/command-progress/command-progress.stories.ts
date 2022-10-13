import {Meta, Story} from '@storybook/angular/types-6-0';
import {moduleMetadata} from "@storybook/angular";

import {CommandProgressComponent} from './command-progress.component'
import {ExecutionDto} from "@fabric/models";

export default {
    title: 'Fabric/Common/Styleguide/CommandProgress',
    component: CommandProgressComponent,
    argTypes: {
    },
    decorators: [
        moduleMetadata({
            declarations: [
                CommandProgressComponent
            ],
            imports: [
            ]
        }),
    ],

} as Meta;

const CommandProgressComponentStory: Story<CommandProgressComponent> = (args: CommandProgressComponent) => ({
    component: CommandProgressComponent,
    props: args,
})

export const CommandIsStarted = CommandProgressComponentStory.bind({});
CommandIsStarted.args = {
    command: <ExecutionDto>{
        label: 'upload snapshot to DEV',
        percentDone: 25,
        status: 'RUNNING',
        children: [
            {
                label: 'MONGO',
                percentDone: 25,
                status: 'RUNNING',
            },
            {
                label: 'ELASTIC',
                percentDone: 25,
                status: 'RUNNING',
            },
        ]
    }
}

export const SubcommandPaused = CommandProgressComponentStory.bind({});
SubcommandPaused.args = {
    command: <ExecutionDto>{
        label: 'upload snapshot to DEV',
        percentDone: 50,
        status: 'RUNNING',
        children: [
            {
                label: 'MONGO',
                percentDone: 100,
                status: 'DONE',
            },
            {
                label: 'ELASTIC',
                percentDone: 25,
                status: 'PAUSED',
            },
        ]
    }
}

export const SubcommandUnresponsive = CommandProgressComponentStory.bind({});
SubcommandUnresponsive.args = {
    command: <ExecutionDto>{
        label: 'upload snapshot to DEV',
        percentDone: 60,
        status: 'UNRESPONSIVE',
        children: [
            {
                label: 'MONGO',
                percentDone: 100,
                status: 'DONE',
            },
            {
                label: 'ELASTIC',
                percentDone: 25,
                status: 'UNRESPONSIVE',
            },
        ]
    }
}


export const SubcommandError = CommandProgressComponentStory.bind({});
SubcommandError.args = {
    command: <ExecutionDto>{
        label: 'upload snapshot to DEV',
        percentDone: 75,
        status: 'ERROR',
        children: [
            {
                label: 'MONGO',
                percentDone: 100,
                status: 'DONE',
            },
            {
                label: 'ELASTIC',
                percentDone: 75,
                status: 'ERROR',
            },
        ]
    }
}

export const SubcommandDone = CommandProgressComponentStory.bind({});
SubcommandDone.args = {
    command: <ExecutionDto>{
        label: 'upload snapshot to DEV',
        percentDone: 100,
        status: 'DONE',
        children: [
            {
                label: 'MONGO',
                percentDone: 100,
                status: 'DONE',
            },
            {
                label: 'ELASTIC',
                percentDone: 100,
                status: 'DONE',
            },
        ]
    }
}

