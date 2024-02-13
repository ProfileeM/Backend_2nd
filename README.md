# 프로젝트 브랜치 전략

## master (또는 main)

- `master` 브랜치는 제품의 안정된 버전을 관리합니다. 항상 배포 가능한 상태를 유지하며, 이 브랜치로의 머지는 신중하게 이루어져야 합니다.
- 직접적인 커밋은 피하고, 주로 태그를 통해 안정된 버전을 관리합니다.
- 배포 전 테스트 및 검증이 완료된 코드만 `master` 브랜치로 머지되어야 합니다.

## develop

- `develop` 브랜치는 현재 진행 중인 모든 작업들이 통합되는 곳입니다. 새로운 기능이나 작업이 개발되면 해당 브랜치로 머지되어야 합니다.
- 직접적인 커밋보다는 주로 기능 브랜치에서의 머지를 통해 업데이트가 이루어져야 합니다.
- `develop` 브랜치의 안정성을 유지하기 위해 필요한 테스트를 수행해야 합니다.

## feature branches

- 각 `feature` 브랜치는 특정 기능 또는 작업을 개발하는 데 사용됩니다.
- 완료된 기능은 `develop` 브랜치로 머지되어야 하며, 해당 머지 이전에 필요한 단위 테스트 및 통합 테스트가 이루어져야 합니다.
- 불필요한 커밋을 최소화하고, 작업이 완료되면 브랜치를 삭제합니다.

## release branches

- `release` 브랜치는 배포를 준비하는 데 사용됩니다. 배포 전 테스트 및 버그 수정이 이루어집니다.
- 배포 준비가 완료되면 `release` 브랜치를 `master` 브랜치로 머지하고, 새로운 태그를 생성하여 안정된 버전으로 배포합니다.
- 불필요한 변경사항을 최소화하고, 버전 번호 및 문서 업데이트를 신중하게 수행합니다.

이 프로젝트의 브랜치를 안전하게 사용하기 위해서는 각 브랜치에 대한 주의사항을 엄격히 준수해야 합니다. 브랜치 전략에 따라 협업하면서 효과적으로 프로젝트를 관리할 수 있습니다.