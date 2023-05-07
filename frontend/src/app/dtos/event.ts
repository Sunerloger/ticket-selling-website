export interface Event {
  id?: number;
  title: string;
  date: Date;
  startTime: Date;
  cityname: string;
  areaCode: number;
  duration: number;
  category: string;
  address: string;
  description?: string;
  image?: string;
}
