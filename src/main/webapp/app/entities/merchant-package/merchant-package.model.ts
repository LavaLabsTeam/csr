import { BaseEntity } from './../../shared';

export class MerchantPackage implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public description?: string,
        public packageIncludes?: any,
        public termsAndCondition?: any,
        public price?: number,
        public oldPrice?: number,
        public startDate?: any,
        public endDate?: any,
        public photoContentType?: string,
        public photo?: any,
        public merchant?: BaseEntity,
        public category?: BaseEntity,
    ) {
    }
}
