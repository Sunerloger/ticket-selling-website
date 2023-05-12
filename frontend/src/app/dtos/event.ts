export interface Event {
  id?: number;
  title: string;
  date: Date;
  startTime: string;
  cityname: string;
  areaCode: number;
  duration: string;
  category: string;
  address: string;
  description?: string;
  image?: string;
}
