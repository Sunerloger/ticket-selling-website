import { Seat } from './roomplan';

export interface Section{
    name: string;
    color: string;
    price: number;
}

export interface DetailedSection{
    name: string;
    color: string;
    price: number;
    seat: Seat;
}

export interface PersistedSection extends Section{
    id: number;
}
