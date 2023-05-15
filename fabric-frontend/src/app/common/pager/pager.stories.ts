import {Meta, Story} from '@storybook/angular/types-6-0';
import {moduleMetadata} from "@storybook/angular";
import {PagerComponent} from "./pager.component";
import {FontAwesomeModule} from "@fortawesome/angular-fontawesome";
import {PagingDto} from "@fabric/models";

export default {
    title: 'Fabric/Common/Pager',
    component: PagerComponent,
    argTypes: {},
    decorators: [
        moduleMetadata({
            declarations: [
                PagerComponent
            ],
            imports: [
                FontAwesomeModule,
            ]
        }),
    ],

} as Meta;

const PagerComponentStory: Story<PagerComponent> = (args: PagerComponent) => ({
    component: PagerComponent,
    props: args,

})

export const Case_1_10_1 = PagerComponentStory.bind({});
Case_1_10_1.args = {
    paging: <PagingDto> {
        nrOfResults: 1,
        pageSize: 10,
        pageNr: 1,
    }

}

export const Case_2_10_1 = PagerComponentStory.bind({});
Case_2_10_1.args = {
    paging: <PagingDto> {
        nrOfResults: 2,
        pageSize: 10,
        pageNr: 1,
    }

}

export const Case_40_10_1 = PagerComponentStory.bind({});
Case_40_10_1.args = {
    paging: <PagingDto> {
        nrOfResults: 40,
        pageSize: 10,
        pageNr: 1,
    }

}

export const Case_40_10_3 = PagerComponentStory.bind({});
Case_40_10_3.args = {
    paging: <PagingDto> {
        nrOfResults: 40,
        pageSize: 10,
        pageNr: 3,
    }

}



export const Case_55_10_1 = PagerComponentStory.bind({});
Case_55_10_1.args = {
    paging: <PagingDto> {
        nrOfResults: 55,
        pageSize: 10,
        pageNr: 1,
    }

}

export const Case_55_10_2 = PagerComponentStory.bind({});
Case_55_10_2.args = {
    paging: <PagingDto> {
        nrOfResults: 55,
        pageSize: 10,
        pageNr: 2,
    }

}

export const Case_55_10_3 = PagerComponentStory.bind({});
Case_55_10_3.args = {
    paging: <PagingDto> {
        nrOfResults: 55,
        pageSize: 10,
        pageNr: 3,
    }

}

export const Case_55_10_5 = PagerComponentStory.bind({});
Case_55_10_5.args = {
    paging: <PagingDto> {
        nrOfResults: 55,
        pageSize: 10,
        pageNr: 5,
    }

}

export const Case_55_10_6 = PagerComponentStory.bind({});
Case_55_10_6.args = {
    paging: <PagingDto> {
        nrOfResults: 55,
        pageSize: 10,
        pageNr: 6,
    }

}
