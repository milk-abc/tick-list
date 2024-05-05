export type LocaleType = 'zh_CN' | 'en' | 'ru' | 'ja' | 'ko'

export interface MultiTabsSetting {
  cache: boolean
  show: boolean
  showQuick: boolean
  canDrag: boolean
  showRedo: boolean
  showFold: boolean
  autoCollapse: boolean
}

export interface GlobConfig {
  // Site title
  title: string
  // Service interface url
  apiUrl: string
  // Upload url
  uploadUrl?: string
  //  Service interface url prefix
  urlPrefix?: string
  // Project abbreviation
  shortName: string
}
export interface GlobEnvConfig {
  // Site title
  VITE_GLOB_APP_TITLE: string
  // Service interface url
  VITE_GLOB_API_URL: string
  // Service interface url prefix
  VITE_GLOB_API_URL_PREFIX?: string
  // Upload url
  VITE_GLOB_UPLOAD_URL?: string
}
