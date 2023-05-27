import {EventDate} from './eventDate';

export interface Event {
  id?: number;
  title: string;
  eventDatesLocation: EventDate[];
  duration: string;
  category: string;
  artist: string;
  description?: string;
  image?: string;
}
