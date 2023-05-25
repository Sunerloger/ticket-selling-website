export class News {
  id?: number;
  title: string;
  shortText: string;
  fullText: string;
  createdAt?: string;
  coverImage?: string;
  images: string[];
}

export class AbbreviatedNews {
  id: number;
  title: string;
  shortText: string;
  createdAt: string;
  coverImage?: string;
}
