import {Meta, Story} from '@storybook/angular/types-6-0';
import {moduleMetadata} from "@storybook/angular";

import {EntityItem, MetaModelViewComponent} from './meta-model-view.component'
import {FontAwesomeModule} from "@fortawesome/angular-fontawesome";

export default {
    title: 'Fabric/SystemDescriptionPage/MetaModelView',
    component: MetaModelViewComponent,
    argTypes: {},
    decorators: [
        moduleMetadata({
            declarations: [
                MetaModelViewComponent,
            ],
            imports: [
                FontAwesomeModule,
            ]
        }),
    ],

} as Meta;

const MetaModelViewComponentStory: Story<MetaModelViewComponent> = (args: MetaModelViewComponent) => ({
    component: MetaModelViewComponent,
    props: args,
})

export const JDBC = MetaModelViewComponentStory.bind({});
JDBC.args = {
    entity: <EntityItem>{
        name: 'Sales DB Schema',
        attributes: [
            {
                name: 'Tables',
                entries: [
                    {
                        entity_id: "Table::Customers",
                        name: 'Customers',
                        attributes: [
                            {
                                name: 'Columns',
                                entries: [
                                    {
                                        name: 'customer_id',
                                    },
                                    {
                                        name: 'name',
                                    }
                                ]
                            },
                            {
                                name: 'Primary Key',
                                entries: [
                                    {
                                        name: 'customer_id',
                                    }
                                ]
                            }
                        ]
                    },
                    {
                        name: 'Products',
                        attributes: [
                            {
                                name: 'Columns',
                                entries: [
                                    {
                                        name: 'product_id',
                                    },
                                    {
                                        name: 'name',
                                    },
                                    {
                                        name: 'price',
                                    },
                                ]
                            },
                            {
                                name: 'Primary Key',
                                entries: [
                                    {
                                        name: 'product_id',
                                    }
                                ]
                            },

                        ]
                    },
                    {
                        name: 'Orders',
                        attributes: [
                            {
                                name: 'Columns',
                                entries: [
                                    {
                                        name: 'order_id',
                                    },
                                    {
                                        name: 'product_id',
                                    },
                                    {
                                        name: 'customer_id',
                                    },
                                    {
                                        name: 'quantity',
                                    }
                                ]
                            },
                            {
                                name: 'Primary Key',
                                entries: [
                                    {
                                        name: 'order_id',
                                    }
                                ]
                            },
                            {
                                name: 'Foreign Keys',
                                entries: [
                                    {
                                        name: 'fk_product_customer',
                                        attributes: [
                                            {
                                                name: 'Table',
                                                entries: [
                                                    {
                                                        entity_ref: 'Table::Customers',
                                                        name: 'Customers'
                                                    }
                                                ]
                                            }
                                        ]
                                    }
                                ]
                            },

                        ]
                    }
                ]
            }
        ]
    }
}