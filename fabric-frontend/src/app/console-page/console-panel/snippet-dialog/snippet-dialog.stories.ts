import {Meta, Story} from '@storybook/angular/types-6-0';
import {moduleMetadata} from "@storybook/angular";

import {SnippetDialogComponent, SnippetDialogSpec} from './snippet-dialog.component'

export default {
  title: 'Fabric/Console/SnippetDialog',
  component: SnippetDialogComponent,
  argTypes: {
  },
  decorators: [
    moduleMetadata({
      declarations: [
        SnippetDialogComponent
      ],
      imports: [
      ]
    }),
  ],

} as Meta;

const SnippetDialogComponentStory: Story<SnippetDialogComponent> = (args: SnippetDialogComponent) => ({
  component: SnippetDialogComponent,
  props: args,
})

export const Case1 = SnippetDialogComponentStory.bind({});
Case1.args = {

  spec: <SnippetDialogSpec> {

    data: {
      attributes:<{ [key: string]: string }> {
        httpMethod: "GET",
        endpoint: "/_cat/indices?format=json",
        jsonEntity: undefined
      }
    },

    descriptor: {
      "items": [
        {
          "inputType": "SELECT",
          "name": "httpMethod",
          "selectValues": [
            "GET",
            "POST",
            "PUT"
          ]
        },
        {
          "inputType": "TEXT",
          "name": "endpoint",
          "selectValues": null
        },
        {
          "inputType": "TEXTAREA",
          "name": "jsonEntity",
          "selectValues": null
        }
      ]
    },



  }
}

